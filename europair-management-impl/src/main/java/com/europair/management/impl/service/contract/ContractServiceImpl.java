package com.europair.management.impl.service.contract;

import com.europair.management.api.dto.contract.ContractDto;
import com.europair.management.api.enums.ContractStatesEnum;
import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.enums.FileStatesEnum;
import com.europair.management.api.enums.PurchaseSaleEnum;
import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.impl.common.service.IStateChangeService;
import com.europair.management.impl.mappers.contract.IContractMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.contracts.entity.Contract;
import com.europair.management.rest.model.contracts.repository.ContractRepository;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Random;

@Service
@Transactional
public class ContractServiceImpl implements IContractService {

    private final String FILE_ID_FILTER = "file.id";

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private IStateChangeService stateChangeService;

    @Override
    public Page<ContractDto> findAllPaginatedByFilter(final Long fileId, Pageable pageable, CoreCriteria criteria) {
        checkIfFileExists(fileId);
        Utils.addCriteriaIfNotExists(criteria, FILE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(fileId));

        return contractRepository.findContractByCriteria(criteria, pageable)
                .map(IContractMapper.INSTANCE::toDto);
    }

    @Override
    public ContractDto findById(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        return IContractMapper.INSTANCE.toDto(contractRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract not found with id: " + id)));
    }

    @Override
    public ContractDto saveContract(final Long fileId, ContractDto contractDto) {
        checkIfFileExists(fileId);
        contractDto.setFileId(fileId);
        if (contractDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New Contract expected. Identifier %s got", contractDto.getId()));
        }

        Contract contract = IContractMapper.INSTANCE.toEntity(contractDto);
        contract = contractRepository.save(contract);

        return IContractMapper.INSTANCE.toDto(contract);
    }

    @Override
    public ContractDto updateContract(final Long fileId, Long id, ContractDto contractDto) {
        checkIfFileExists(fileId);
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract not found with id: " + id));
        IContractMapper.INSTANCE.updateFromDto(contractDto, contract);
        contract = contractRepository.save(contract);

        return IContractMapper.INSTANCE.toDto(contract);
    }

    @Override
    public void deleteContract(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        if (!contractRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract not found with id: " + id);
        }
        contractRepository.deleteById(id);
    }

    @Override
    public void generateContracts(Long fileId, Long routeId) {
        File file = fileRepository.findById(fileId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId));
        Route route = routeRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));
        if (!RouteStatesEnum.WON.equals(route.getRouteState())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "Cannot generate contracts. The route is not in the state WON.");
        }

        Contribution contributionWon = route.getContributions().stream()
                .filter(contribution -> ContributionStatesEnum.WON.equals(contribution.getContributionState()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "No Won contributions found on route."));

        // Purchase contract
        Contract purchaseContract = new Contract();
        purchaseContract.setFileId(fileId);
        purchaseContract.setContributionId(contributionWon.getId());
        purchaseContract.setContractType(PurchaseSaleEnum.PURCHASE);
        purchaseContract.setCode(generateContractCode());
        purchaseContract.setContractState(ContractStatesEnum.PENDING);
        purchaseContract.setProviderId(file.getProviderId());
        purchaseContract.setPrice(contributionWon.getPurchasePrice());
        purchaseContract.setVatPercentage(contributionWon.getPurchaseCommissionPercent());
        purchaseContract.setVatAmount(contributionWon.getVatAmountOnPurchase());
        purchaseContract.setCurrency(contributionWon.getCurrency());

        purchaseContract = contractRepository.save(purchaseContract);

        // Sale contract
        Contract saleContract = new Contract();
        saleContract.setFileId(fileId);
        saleContract.setContributionId(contributionWon.getId());
        saleContract.setContractType(PurchaseSaleEnum.SALE);
        saleContract.setCode(generateContractCode());
        saleContract.setContractState(ContractStatesEnum.PENDING);
        saleContract.setClientId(file.getClientId());
        saleContract.setPrice(contributionWon.getSalesPrice());
        saleContract.setVatPercentage(contributionWon.getSalesCommissionPercent());
        saleContract.setVatAmount(contributionWon.getVatAmountOnSale());
        saleContract.setCurrency(contributionWon.getCurrencyOnSale());

        saleContract = contractRepository.save(saleContract);

        // ToDo: generar servicios adicionales


        // Change file state
        stateChangeService.changeState(Collections.singletonList(fileId), FileStatesEnum.BLUE_BOOKED);

    }

    private void checkIfFileExists(final Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId);
        }
    }

    private String generateContractCode() {
        // ToDo: como generamos el codigo??
        Random random = new Random();
        return "TEST-" + random.nextInt(999999);
    }

}
