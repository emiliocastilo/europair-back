
/*
 Queries para generar inserts de carga de datos desde la bdd de pro a nuestra bdd

 PRO DB LENGTH UNITS: 
 0 - FOOT
 1 - METER
 2 - KM
 3 - NM
 4 - INCH

 PRO DB SPEED UNITS: 
 0 - KM/H
 1 - KNOTS

*/

-- COUTRIES - [Europair Broker$Country_Region]
select CONCAT('INSERT INTO countries(code, name, european_union, created_at, created_by) VALUES(',
'"', Code, '", ',
'"', Name, '", ',
IIF(
Code in ('DE', 'AT', 'BE', 'BG', 'CY', 'HR', 'DK', 'SK', 'SI', 'ES', 'EE', 'FI', 'FR', 'EL', 'HU', 'IE', 'IT', 'LV', 'LT', 'LU', 'MT', 'NL', 'PL', 'PT', 'CZ', 'RO', 'SE'),
'true', 'false'), ', now(), "System");')
from [Europair Broker$Country_Region] ebcr;


-- CITIES - [Europair Broker$Cities]
select CONCAT('INSERT INTO cities(code, name, country_id, canary_islands, created_at, created_by) VALUES(',
'"', Code, '", ',
'"', Name, '", ',
'(SELECT id from countries where code="', [Country ID], '"), false, now(), "System");')
from [Europair Broker$Cities] ebc;


-- AIRCRAFT_CATEGORIES - [Europair Broker$Aircraft Categories] - [Europair Broker$Aircraft Subcategories]
select CONCAT('insert into aircraft_categories(category_code, category_name, created_at, created_by) values(',
'"', Code, '", ',
'"', Name, '", ',
'now(), "System");')
from [Europair Broker$Aircraft Categories] ebac;

select CONCAT('insert into aircraft_categories(category_code, category_name, category_order, parent_category_id, created_at, created_by) values(',
'"', Code, '", ',
'"', Name, '", ',
[Order], ', ',
'(select id from aircraft_categories ac where ac.category_code="', [Category ID], '"), ',
'now(), "System");')
from [Europair Broker$Aircraft Subcategories] ebas;


-- OPERATORS - [Europair Broker$Operators]
select concat('insert into operators(iata_code, icao_code, name, aoc_last_revision_date, insurance_expiration_date, created_at, created_by) values(',
'"', [IATA Code], '", ',
'"', [ICAO Code], '", ',
'"', Name, '", ',
iif(year([AOC expiration]) < 1970, 'null', concat('timestamp("', convert(varchar, [AOC expiration], 121), '")')), ', ',
iif(year([Aircraft insurance expiration]) < 1970, 'null', concat('timestamp("', convert(varchar, [Aircraft insurance expiration], 121), '")')), ', ',
'now(), "System");')
from [Europair Broker$Operators] ebo;


-- AIRCRAFT_TYPES - [Europair Broker$Aircrafts Types]
select concat('insert into aircraft_types(iata_code, icao_code, type_code, description, manufacturer, category_id, subcategory_id, flight_range, flight_range_unit, cabin_width, cabin_width_unit, cabin_height, cabin_height_unit, cabin_length, cabin_length_unit, max_cargo, created_at, created_by) values(',
'null, ',
'null, ',
'"', Code, '", ',
'"', Name, '", ',
'"', Manufacturer, '", ',
'(select id from aircraft_categories ac where ac.category_code="', [Category ID], '"), ',
iif(LEN([Subcategory ID]) < 1, 'null', concat('(select id from aircraft_categories ac where ac.category_code="', [Subcategory ID], '")')), ', ',
[Flight Range], ', ',
iif([Flight Range Units] = 0, '"FOOT"',
  iif([Flight Range Units] = 1, '"METER"',
    iif([Flight Range Units] = 2, '"KILOMETER"',
      iif([Flight Range Units] = 3, '"NAUTIC_MILE"',
        iif([Flight Range Units] = 4, '"INCH"', 'null'))))), ', ',
[Input Cabin Width], ', "METER", ', 
[Input Cabin Height], ', "METER", ',
[Input Cabin Length], ', "METER", ',
[Max Load (Kg)], ', ',
'now(), "System");')
from [Europair Broker$Aircrafts Types] ebat;


-- AIRCRAFT_TYPE_AVERAGE_SPEED - [Europair Broker$Aircrafts Types Avg_ Speed]
select concat('insert into aircraft_type_average_speed(aircraft_type_id, from_distance, to_distance, distance_unit, average_speed, average_speed_unit, created_at, created_by) values(',
'(select id from aircraft_types where type_code="', [Aircraft Type ID], '"), ',
[Distance From], ', ',
[Distance To], ', ',
iif([Distance Units] = 0, '"FOOT"',
  iif([Distance Units] = 1, '"METER"',
    iif([Distance Units] = 2, '"KILOMETER"',
      iif([Distance Units] = 3, '"NAUTIC_MILE"',
        iif([Distance Units] = 4, '"INCH"', 'null'))))), ', ',
[Average Speed], ', ',
iif([Average Speed Units] = 0, '"KILOMETERS_HOUR"',
  iif([Average Speed Units] = 1, '"KNOTS"', 'null')), ', ',
'now(), "System");')
from [Europair Broker$Aircrafts Types Avg_ Speed] ebatas
where len([Aircraft Type ID]) > 0;


-- AIRCRAFTS - [Europair Broker$Fleets_Aircrafts]
/*
Notas: 
  Hay que comentar algunos inserts:
  - Actualmente hay una aeronave con operador-iata=SPM que no existe en la tabla operadores
  - Actualmente hay una aeronave sin tipo de aeronave (cod='')
*/
select concat('insert into aircrafts(operator_id, aircraft_type_id, plate_number, production_year, quantity, insurance_end_date, ambulance, nighttime_configuration, notes, seating_f, seating_c, seating_y, inside_upgrade_year, outside_upgrade_year, created_at, created_by) values(',
'(select id from operators where iata_code="', [Operator ID], '"), ',
'(select id from aircraft_types where type_code="', [Aircraft Type ID], '"), ',
iif(len(Registration) > 0, concat('"', Registration, '"'), 'null'), ', ',
[Manufactured Year], ', ',
Quantity, ', ',
'cast("', convert(varchar, [Insurance expiration], 121), '" as datetime), ',
iif([Medical Equipment] = 1, 'true', 'false'), ', ',
[Beds Quantitiy], ', ',
iif(len([Short Notes]) > 0, concat('"', replace([Short Notes], '"', '\"'), '"'), 'null'), ', ',
[Seats F], ', ',
[Seats C], ', ',
[Seats Y], ', ',
year([Indoor Refurbished]), ', ',
year([Outdoor Refurbished]), ', ',
'now(), "System");')
from [Europair Broker$Fleets_Aircrafts] ebfa;


-- AIRPORTS - [Europair Broker$Airports]
/*
Notas: 
  Hay que comentar algunos inserts:
  - Hay aeropuertos con código icao ([Auxiliar Code]) duplicados: ESNV (2), LTFG (2), URMT (2)
*/
select CONCAT('insert into airports(iata_code, icao_code, name, country_id, city_id, time_zone, elevation, elevation_unit, latitude, longitude, customs, special_conditions, flight_rules, created_at, created_by) values(',
iif(len([Main Code]) < 4, concat('"', [Main Code], '"'), 'null'), ', ',
iif(len([Auxiliar Code]) < 5, concat('"', [Auxiliar Code], '"'), 'null'), ', ',
'"', Name, '", ',
'(select id from countries where code="', [Country ID], '"), ',
'(select id from cities where code="', [City ID], '" and country_id=(select id from countries where code="', [Country ID], '")), ',
iif(len(eba.[Time Zone ID]) < 1, 'null', 
	concat('"UTC ', iif(ebtz.[Initial Gap Type] = 0, '+', '-'), format(ebtz.[Initial Gap], 'hh:mm'), '"')), ', ',
Elevation, ', "FOOT", ',
(([Latitude Degrees]+([Latitude Minutes]/60.0)+([Latitude Seconds]/3600.0))* iif([Latitude Type]=1,-1,1)), ', ',
(([Longitude Degrees]+([Longitude Minutes]/60.0)+([Longitude Seconds]/3600.0))* iif([Longitude Type]=1,-1,1)), ', ',
IIF(Customs = 0, '"YES"', '"NO"'), ', ',
IIF([Especial Certification] = 0, 'false', 'true'), ', ',
IIF([Flight Rules Type] = 0, '"VFR"', '"IFR"'), ', ',
'now(), "System");')
from [Europair Broker$Airports] eba
left join [Europair Broker$Time Zone] ebtz on eba.[Time Zone ID]=ebtz.[Code];


-- AIRPORTS TERMINALS- [Europair Broker$Airports Terminals]
select CONCAT('insert into terminals(code, name, airport_id, observation, created_at, created_by) values(',
'"', [Terminal Code], '", ',
'"', Name, '", ',
'(select id from airports where ', iif(len([Airport ID]) = 3, 'iata_code', 'icao_code'), '="', [Airport ID], '"), ',
iif(len(Remarks) > 0, concat('"', replace(Remarks, '"', '\"'), '"'), 'null'), ', ',
'now(), "System");')
from [Europair Broker$Airports Terminals] ebat;


-- AIRPORTS RUNWAYS- [Europair Broker$Airports] (dentro de las columnas están los datos de la pista principal)
/*
Notas: 
  Hay que comentar algunos inserts:
  - Hay aeropuertos con código icao ([Auxiliar Code]) duplicados: ESNV (2), LTFG (2), URMT (2)
*/
select CONCAT('insert into runways(name, main_runway, airport_id, runway_length, runway_length_unit, runway_width, runway_width_unit, observation, created_at, created_by) values(',
iif(len([Longest Runway Code]) > 0, concat('"', [Longest Runway Code], '"'), 'null'), ', ',
'true, ',
'(select id from airports where ', iif(len([Main Code]) < 4, 'iata_code', 'icao_code'), '="', 
  iif(len([Main Code]) < 4, [Main Code], [Auxiliar Code]), '"), ',
[Input Runway Length], ', ',
iif([Input Runway Units] = 0, '"FOOT"',
  iif([Input Runway Units] = 1, '"METER"',
    iif([Input Runway Units] = 2, '"KILOMETER"',
      iif([Input Runway Units] = 3, '"NAUTIC_MILE"',
        iif([Input Runway Units] = 4, '"INCH"', 'null'))))), ', ',
[Input Runway Width], ', ',
iif([Input Runway Units] = 0, '"FOOT"',
  iif([Input Runway Units] = 1, '"METER"',
    iif([Input Runway Units] = 2, '"KILOMETER"',
      iif([Input Runway Units] = 3, '"NAUTIC_MILE"',
        iif([Input Runway Units] = 4, '"INCH"', 'null'))))), ', ',
iif(len([Longest Runway Remarks]) > 0, concat('"', replace([Longest Runway Remarks], '"', '\"'), '"'), 'null'), ', ',
'now(), "System");')
from [Europair Broker$Airports] eba;
