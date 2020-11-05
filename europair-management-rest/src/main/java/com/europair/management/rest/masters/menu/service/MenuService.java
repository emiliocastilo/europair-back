package com.europair.management.rest.masters.menu.service;

import com.europair.management.api.dto.menu.MenuOptionDto;
import com.europair.management.impl.mappers.masters.menu.MenuOptionMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.audit.AuditorAwareImpl;
import com.europair.management.rest.masters.menu.repository.MenuOptionRepository;
import com.europair.management.rest.model.masters.menu.entity.MenuOption;
import com.europair.management.rest.model.roles.entity.Role;
import com.europair.management.rest.model.screens.entity.Screen;
import com.europair.management.rest.model.tasks.entity.Task;
import com.europair.management.rest.model.users.entity.User;
import com.europair.management.rest.model.users.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuService.class);

    private final MenuOptionRepository menuOptionRepository;
    private final IUserRepository userRepository;


    @Autowired
    public MenuService(MenuOptionRepository menuOptionRepository, IUserRepository userRepository) {
        this.menuOptionRepository = menuOptionRepository;
        this.userRepository = userRepository;
    }

    public MenuOptionDto getMenu(final Long parentMenuOptionId) {
        MenuOption resultMenu = new MenuOption();
        resultMenu.setChilds(new ArrayList<>());

        final Optional<MenuOption> queryResult = this.menuOptionRepository.findById(parentMenuOptionId);

        // we must retrieve the user who request the menu, takes all his task and just send all allowed views for him
        Long userId = Utils.GetUserFromSecurityContext.getLoggedUserId(userRepository);
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found, something when wrong with database please review logs. userId: " + userId));
        List<Role> roleList = user.getRoles();
        List<Task> taskList = user.getTasks();

        Map<Long, Screen> totalMapListScrens = new HashMap<>();

        Map<Long, Screen> screenMapFromRoles = roleList.stream()
                .map(Role::getTasks)
                .flatMap(Collection::stream)
                .map(Task::getScreens)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Screen::getId,
                        screen -> screen,
                        (screen1, screen2) -> {
                            LOGGER.debug("Duplicate screen take first occurrence");
                            return screen1;
                        }
                ));

        Map<Long, Screen> screenMapFromTasks = taskList.stream()
                .map(Task::getScreens)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Screen::getId,
                        screen -> screen,
                        (screen1, screen2) -> {
                            LOGGER.debug("Duplicate screen take first occurrence");
                            return screen1;
                        }
                ));

        totalMapListScrens.putAll(screenMapFromRoles);
        totalMapListScrens.putAll(screenMapFromTasks);

        // NOTE: the menu only has 2 lvl of nodes
        // TODO: apply recursion
        // must iterate over the screens and see if someone is in the path of the menu list
        if (queryResult.isPresent()) {
            // queryResult is de godfather of the menu, the first node of a tree
            resultMenu = copyMenuOption(queryResult.get(), totalMapListScrens);

            List<MenuOption> newChildsLvl1 = new ArrayList<>();
            for (MenuOption menuOptionLvl1 : queryResult.get().getChilds()) {
                // now we are at the first lvl of nodes who group the different options of the menu
                boolean keepAliveLvl1 = false;
                List<MenuOption> newChildsLvl2 = new ArrayList<>();
                for (MenuOption menuOptionLvl2 : menuOptionLvl1.getChilds()) {
                    // now we are at the seccond lvl of nodes:
                    if (totalMapListScrens.containsKey(menuOptionLvl2.getScreenId())) {
                        keepAliveLvl1 = true;
                        MenuOption newMenuOptionlvl2 = copyMenuOption(menuOptionLvl2, totalMapListScrens);
                        newChildsLvl2.add(newMenuOptionlvl2);
                    }
                }

                if (keepAliveLvl1) {
                    MenuOption newMenuOptionlvl1 = copyMenuOption(menuOptionLvl1, totalMapListScrens);
                    newMenuOptionlvl1.setChilds(newChildsLvl2);
                    newChildsLvl1.add(newMenuOptionlvl1);
                }
            }

            applyOrderByWeightOnLvl1(newChildsLvl1);

            resultMenu.setChilds(newChildsLvl1);


            return MenuOptionMapper.INSTANCE.toDto(resultMenu);
        } else {
            return null;
        }
    }

    /**
     * This method applies natural order over a List<MenuOption>
     * @param menuOptionList
     */
    private void applyOrderByWeightOnLvl1(List<MenuOption> menuOptionList) {

        Collections.sort(menuOptionList, (child1,child2) -> {
            return child1.getWeight() - child2.getWeight();
        });

    }

    private MenuOption copyMenuOption(MenuOption menuOptionOrigin, Map<Long, Screen> totalMapListScrens) {
        MenuOption resultMenu = new MenuOption();

        resultMenu.setId(menuOptionOrigin.getId());
        resultMenu.setLabel(menuOptionOrigin.getLabel());
        resultMenu.setWeight(menuOptionOrigin.getWeight());
        resultMenu.setIcon(menuOptionOrigin.getIcon());
        resultMenu.setName(menuOptionOrigin.getName());
        resultMenu.setParent(menuOptionOrigin.getParent());
        resultMenu.setScreenId(menuOptionOrigin.getScreenId());

        //this param is maintained to keep alive the menu on the Angular application must be changed if the menu evolves
        if (null != totalMapListScrens.get(menuOptionOrigin.getScreenId())) {
            resultMenu.setRoute(totalMapListScrens.get(menuOptionOrigin.getScreenId()).getRoute());
        }

        return resultMenu;
    }

}
