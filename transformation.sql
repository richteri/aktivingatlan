DROP TABLE `zzz_accomodation_type`, `zzz_property_type`;
DELETE FROM `zzz_property_feature` WHERE `features_id` > 7;
DROP TABLE `zzz_feature`;

UPDATE `zzz_property` SET type_id = -type_id;
UPDATE `zzz_property` SET `type_id`=1 WHERE `type_id`=-15;
UPDATE `zzz_property` SET `type_id`=2 WHERE `type_id`=-1;
UPDATE `zzz_property` SET `type_id`=3 WHERE `type_id`=-2;
UPDATE `zzz_property` SET `type_id`=4 WHERE `type_id`=-3;
UPDATE `zzz_property` SET `type_id`=5 WHERE `type_id`=-4;
UPDATE `zzz_property` SET `type_id`=6 WHERE `type_id`=-9;
UPDATE `zzz_property` SET `type_id`=7 WHERE `type_id`=-14;
UPDATE `zzz_property` SET `type_id`=8 WHERE `type_id`=-5;
UPDATE `zzz_property` SET `type_id`=9 WHERE `type_id`=-6;
UPDATE `zzz_property` SET `type_id`=10 WHERE `type_id`=-7;
UPDATE `zzz_property` SET `type_id`=11 WHERE `type_id`=-16;
UPDATE `zzz_property` SET `type_id`=12 WHERE `type_id`=-11;
UPDATE `zzz_property` SET `type_id`=13 WHERE `type_id`=-12;
UPDATE `zzz_property` SET `type_id`=14 WHERE `type_id`=-8;
UPDATE `zzz_property` SET `type_id`=15 WHERE `type_id`=-13;
UPDATE `zzz_property` SET `type_id`=16 WHERE `type_id`=-10;
UPDATE `zzz_property` SET `type_id`=17 WHERE `type_id`=-17;

ALTER TABLE `zzz_property` CHANGE `type_id` `category_id` INT(11);
ALTER TABLE `zzz_property` ADD `active` BIT(1) NOT NULL AFTER `code`, ADD `featured` BIT(1) NOT NULL AFTER `active`;
UPDATE `zzz_property` SET `active`=1 WHERE `suspended`!='t';
UPDATE `zzz_property` SET `active`=0 WHERE `suspended`='t';
UPDATE `zzz_property` SET `featured`=1 WHERE `special`='t';
UPDATE `zzz_property` SET `featured`=0 WHERE `special`!='t';
ALTER TABLE `zzz_property` DROP COLUMN `suspended`;
ALTER TABLE `zzz_property` DROP COLUMN `special`;

ALTER TABLE `zzz_property` ADD `city_id` INT(11) AFTER `city`;
UPDATE `zzz_property` AS p SET p.city_id = (SELECT c.id FROM city AS c WHERE p.city = c.name);
ALTER TABLE `zzz_property` DROP COLUMN `city`;

ALTER TABLE `zzz_property` ADD `for_sale` BIT(1) DEFAULT 1 AFTER `city_id`;
ALTER TABLE `zzz_property` ADD `sale_huf` DECIMAL(10,2) NOT NULL AFTER `price_eur`, 
	ADD `sale_eur` DECIMAL(10,2) NOT NULL AFTER `sale_huf`;
UPDATE `zzz_property` SET `sale_huf`=`price_huf`, `sale_eur`=`price_eur`;
ALTER TABLE `zzz_property` DROP COLUMN `price_huf`;
ALTER TABLE `zzz_property` DROP COLUMN `price_eur`;
ALTER TABLE `zzz_property` CHANGE `building_area` `floor_area` INT(11) NOT NULL DEFAULT '0', 
	CHANGE `field_area` `parcel_area` INT(11) NOT NULL DEFAULT '0', 
	CHANGE `room_qty1` `room` INT(11) NOT NULL DEFAULT '0', 
	CHANGE `room_qty2` `half_room` INT(11) NOT NULL DEFAULT '0', 
	CHANGE `ins_date` `created_date` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00';
ALTER TABLE `zzz_property` CHANGE `desc_hu` `description_hu` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL, 
	CHANGE `desc_en` `description_en` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL, 
	CHANGE `desc_de` `description_de` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL;

	
ALTER TABLE `zzz_property_feature` CHANGE `property_id` `propertys_id` INT(11) NOT NULL DEFAULT '0', 
	CHANGE `feature_id` `features_id` INT(11) NOT NULL DEFAULT '0';	
	
ALTER TABLE `zzz_property_image` CHANGE `desc_hu` `description_hu` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL, 
	CHANGE `desc_en` `description_en` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL, 
	CHANGE `desc_de` `description_de` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL;	
ALTER TABLE `zzz_property_image` ADD `header` BIT(1) NOT NULL AFTER `initial`;
UPDATE `zzz_property_image` SET header=1 where initial='t';
UPDATE `zzz_property_image` SET header=0 where initial!='t';
ALTER TABLE `zzz_property_image` DROP `initial`;

UPDATE `zzz_property` SET id = id + 1000000;
UPDATE `zzz_property_feature` SET propertys_id = propertys_id + 1000000;
UPDATE `zzz_property_image` SET property_id = property_id + 1000000;

DELETE FROM `aktivingatlan`.`zzz_property_image` WHERE `zzz_property_image`.`id` = 2807;
DELETE FROM `aktivingatlan`.`zzz_property_image` WHERE `zzz_property_image`.`id` = 2806;
DELETE FROM `aktivingatlan`.`zzz_property_image` WHERE `zzz_property_image`.`id` = 2805;
DELETE FROM `aktivingatlan`.`zzz_property_image` WHERE `zzz_property_image`.`id` = 2804;
DELETE FROM `aktivingatlan`.`zzz_property_image` WHERE `zzz_property_image`.`id` = 2803;
DELETE FROM `aktivingatlan`.`zzz_property_image` WHERE `zzz_property_image`.`id` = 2802;
DELETE FROM `aktivingatlan`.`zzz_property_image` WHERE `zzz_property_image`.`id` = 2801;
DELETE FROM `aktivingatlan`.`zzz_property_image` WHERE `zzz_property_image`.`id` = 2800;

DELETE FROM `aktivingatlan`.`zzz_property_feature` WHERE `zzz_property_feature`.`id` = 1144;
DELETE FROM `aktivingatlan`.`zzz_property_feature` WHERE `zzz_property_feature`.`id` = 1143;



UPDATE `zzz_accomodation` SET `type_id`=10 WHERE `type_id`=2;
UPDATE `zzz_accomodation` SET `type_id`=18 WHERE `type_id`=3;
UPDATE `zzz_accomodation` SET `type_id`=19 WHERE `type_id`=4;
ALTER TABLE `zzz_accomodation` CHANGE `type_id` `category_id` INT(11);
ALTER TABLE `zzz_accomodation` ADD `active` BIT(1) NOT NULL AFTER `code`;
UPDATE `zzz_accomodation` SET `active`=1 WHERE `suspended`!='t';
UPDATE `zzz_accomodation` SET `active`=0 WHERE `suspended`='t';
ALTER TABLE `zzz_accomodation` DROP COLUMN `suspended`;

ALTER TABLE `zzz_accomodation` ADD `city_id` INT(11) AFTER `city`;
ALTER TABLE `zzz_accomodation` CHANGE `city` `city` VARCHAR(60) CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL;
UPDATE `zzz_accomodation` AS a SET a.city_id = (SELECT c.id FROM city AS c WHERE a.city = c.name);
ALTER TABLE `zzz_accomodation` DROP COLUMN `city`;
ALTER TABLE `zzz_accomodation` ADD `for_rent` BIT(1) DEFAULT 1 AFTER `city_id`;
ALTER TABLE `zzz_accomodation` ADD `rent_huf` DECIMAL(10,2) NOT NULL AFTER `price_eur`, 
	ADD `rent_eur` DECIMAL(10,2) NOT NULL AFTER `rent_huf`;
UPDATE `zzz_accomodation` SET `rent_huf`=`price_offpeak_huf`, `rent_eur`=`price_offpeak_eur`;
ALTER TABLE `zzz_accomodation` ADD `rent_peak_huf` DECIMAL(10,2) NOT NULL AFTER `rent_eur`, 
	ADD `rent_peak_eur` DECIMAL(10,2) NOT NULL AFTER `rent_peak_huf`;
UPDATE `zzz_accomodation` SET `rent_peak_huf`=`price_peak_huf`, `rent_peak_eur`=`price_peak_eur`;
ALTER TABLE `zzz_accomodation` DROP COLUMN `price_huf`;
ALTER TABLE `zzz_accomodation` DROP COLUMN `price_eur`;
ALTER TABLE `zzz_accomodation` DROP COLUMN `price_offpeak_huf`;
ALTER TABLE `zzz_accomodation` DROP COLUMN `price_offpeak_eur`;
ALTER TABLE `zzz_accomodation` DROP COLUMN `price_peak_huf`;
ALTER TABLE `zzz_accomodation` DROP COLUMN `price_peak_eur`;
ALTER TABLE `zzz_accomodation` DROP `shareable`, DROP `parts`;
ALTER TABLE `zzz_accomodation` CHANGE `rooms` `room` INT(11) NOT NULL DEFAULT '0', 
	CHANGE `baths` `bathroom` INT(11) NOT NULL DEFAULT '0', 
	CHANGE `toilets` `toilet` INT(11) NOT NULL DEFAULT '0';
ALTER TABLE `zzz_accomodation` CHANGE `beds` `bed` INT(11) NOT NULL DEFAULT '0';
ALTER TABLE `zzz_accomodation` CHANGE `desc_hu` `description_hu` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL, 
	CHANGE `desc_en` `description_en` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL, 
	CHANGE `desc_de` `description_de` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL;
	
	
ALTER TABLE `zzz_accomodation_part` CHANGE `desc_hu` `description_hu` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL, 
	CHANGE `desc_en` `description_en` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL, 
	CHANGE `desc_de` `description_de` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL;
ALTER TABLE `zzz_accomodation_part` CHANGE `price_offpeak_huf` `rent_huf` DECIMAL(10,2) NOT NULL DEFAULT '0', 
	CHANGE `price_offpeak_eur` `rent_eur` DECIMAL(10,2) NOT NULL DEFAULT '0', 
	CHANGE `price_peak_huf` `rent_peak_huf` DECIMAL(10,2) NOT NULL DEFAULT '0', 
	CHANGE `price_peak_eur` `rent_peak_eur` DECIMAL(10,2) NOT NULL DEFAULT '0';
ALTER TABLE `zzz_accomodation_part`
  DROP `price_huf`,
  DROP `price_eur`;
ALTER TABLE `zzz_accomodation_part` CHANGE `beds` `bed` INT(11) NOT NULL DEFAULT '0';
ALTER TABLE `zzz_accomodation_part`
  DROP `kitchen`,
  DROP `bath`,
  DROP `toilet`;
  
ALTER TABLE `zzz_accomodation_part` CHANGE `accomodation_id` `property_id` INT(11) NOT NULL DEFAULT '0';

ALTER TABLE `zzz_accomodation_image` CHANGE `desc_hu` `description_hu` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL, 
	CHANGE `desc_en` `description_en` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL, 
	CHANGE `desc_de` `description_de` TEXT CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL;
	
ALTER TABLE `zzz_accomodation_image` CHANGE `accomodation_id` `property_id` INT(11) NOT NULL DEFAULT '0';
ALTER TABLE `zzz_accomodation_image` ADD `header` BIT(1) NOT NULL AFTER `initial`;
UPDATE `zzz_accomodation_image` SET header=1 where initial='t';
UPDATE `zzz_accomodation_image` SET header=0 where initial!='t';
ALTER TABLE `zzz_accomodation_image` DROP `initial`;

UPDATE `zzz_accomodation` SET id = id + 2000000;
UPDATE `zzz_accomodation_part` SET property_id = property_id + 2000000;
UPDATE `zzz_accomodation_image` SET property_id = property_id + 2000000;

INSERT INTO `property`(`id`, `code`, `active`, `category_id`, `city_id`, `for_rent`, `bed`, `room`, `bathroom`, `toilet`, `distance`, `parking`, `heating`, `pets`, `description_hu`, `description_en`, `description_de`, `rent_huf`, `rent_eur`, `rent_peak_huf`, `rent_peak_eur`, `created_date`) 
SELECT `id`, `code`, `active`, `category_id`, `city_id`, `for_rent`, `bed`, `room`, `bathroom`, `toilet`, `distance`, `parking`, `heating`, `pets`, `description_hu`, `description_en`, `description_de`, `rent_huf`, `rent_eur`, `rent_peak_huf`, `rent_peak_eur`, `ins_date` FROM `zzz_accomodation`;

INSERT INTO `apartment`(`id`, `property_id`, `bed`, `rent_huf`, `rent_eur`, `rent_peak_huf`, `rent_peak_eur`, `description_hu`, `description_en`, `description_de`)
SELECT `id`, `property_id`, `bed`, `rent_huf`, `rent_eur`, `rent_peak_huf`, `rent_peak_eur`, `description_hu`, `description_en`, `description_de` FROM `zzz_accomodation_part`;

INSERT INTO `property`(`id`, `category_id`, `code`, `active`, `featured`, `city_id`, `for_sale`, `sale_huf`, `sale_eur`, `description_hu`, `description_en`, `description_de`, `floor_area`, `parcel_area`, `room`, `half_room`, `created_date`) 
SELECT `id`, `category_id`, `code`, `active`, `featured`, `city_id`, `for_sale`, `sale_huf`, `sale_eur`, `description_hu`, `description_en`, `description_de`, `floor_area`, `parcel_area`, `room`, `half_room`, `created_date` FROM `zzz_property`;

INSERT INTO `property_feature`(`propertys_id`, `features_id`)
SELECT `propertys_id`, `features_id` FROM `zzz_property_feature`;