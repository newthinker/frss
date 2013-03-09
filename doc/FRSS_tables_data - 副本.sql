--用户信息表
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (1, 'super', 'Gzt3XR0PAto=', 0, 'super', 0, 'nothing', 'nothing', null, 'mc');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (2, 'f1', 'Gzt3XR0PAto=', 6, '工厂', 0, '工厂', '工业部门', 'no', 'super');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (3, 'e1', 'Gzt3XR0PAto=', 5, '专家', 0, '专家', '学院', 'no', 'super');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (4, 'f1', 'Gzt3XR0PAto=', 7, '格式审查员', 3, '格式审查员', '维修中心', 'no', 'super');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (5, 'd1', 'Gzt3XR0PAto=', 4, '分发员', 7, '分发员', '维修中心', 'no', 'super');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (6, 'm1', 'Gzt3XR0PAto=', 3, '军区A', 2, 'm1', '军区A', 'no', 'f1');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (7, 'm2', 'Gzt3XR0PAto=', 3, '军区B', 2, 'm2', '军区B', 'no', 'f1');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (8, 'g1', 'Gzt3XR0PAto=', 2, '集团军A', 1, '集团军A', '集团军A', 'no', 'm1');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (9, 'g2', 'Gzt3XR0PAto=', 2, '集团军B', 1, '集团军B', '集团军B', 'no', 'm2');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (10, 'r1', 'Gzt3XR0PAto=', 1, '团A', 0, '团A', '团A', 'no', 'g1');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (11, 'r2', 'Gzt3XR0PAto=', 1, '团B', 0, '团B', '团B', 'no', 'g1');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (12, 'r3', 'Gzt3XR0PAto=', 1, '团C', 0, '团C', '团C', 'no', 'g1');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (13, 'r4', 'Gzt3XR0PAto=', 1, '团D', 0, '团D', '团D', 'no', 'g2');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (14, 'r5', 'Gzt3XR0PAto=', 1, '团E', 0, '团E', '团E', 'no', 'g2');

--工业部门信息(5)
insert into FRSS_FACTORY_INFO (ID, NAME, ADDRESS, CODE, RANGE, GUARANTEE, GUARDADDRESS, ABILITY, CONTACT, CONTACTWAY, MACHINIST)
values (1, '工厂1', '北京路特1号', 'f1', '装甲车', '特种师01', '北京路2号', '5', '门卫A', '010-12345678', '攻城师A');
insert into FRSS_FACTORY_INFO (ID, NAME, ADDRESS, CODE, RANGE, GUARANTEE, GUARDADDRESS, ABILITY, CONTACT, CONTACTWAY, MACHINIST)
values (2, '工厂2', '南京路2号', 'f2', '重型武器', '步兵团01', '雨花台1号', '4', '门卫B', '025-87654321', '攻城师B');
insert into FRSS_FACTORY_INFO (ID, NAME, ADDRESS, CODE, RANGE, GUARANTEE, GUARDADDRESS, ABILITY, CONTACT, CONTACTWAY, MACHINIST)
values (3, '工厂3', '武汉路3号', 'f3', '便携武器', '二炮01', '珞狮南路5号', '3', '门卫C', '027-12348765', '攻城师C');
insert into FRSS_FACTORY_INFO (ID, NAME, ADDRESS, CODE, RANGE, GUARANTEE, GUARDADDRESS, ABILITY, CONTACT, CONTACTWAY, MACHINIST)
values (4, '工厂4', '长江路7号', 'f4', '后勤装备', '野战兵01', '沿江路4号', '3', '门卫D', '023-67548765', '攻城师D');
insert into FRSS_FACTORY_INFO (ID, NAME, ADDRESS, CODE, RANGE, GUARANTEE, GUARDADDRESS, ABILITY, CONTACT, CONTACTWAY, MACHINIST)
values (5, '工厂5', '黄河路9号', 'f5', '导弹', '炮兵团02', '临河路8号', '3', '门卫E', '029-67548765', '攻城师E');

--装备制造信息表(30)
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (1, 'a1', '炮筒', 1);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (2, 'a2', '履带', 1);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (3, 'a3', '方向盘', 1);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (4, 'a4', '瞄准器', 1);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (5, 'a5', '座椅', 1);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (6, 'a6', '轮子', 1);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (7, 'b1', '突击车', 2);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (8, 'b2', '空降战车', 2);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (9, 'b3', '两栖战车', 2);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (10, 'b4', '布雷车', 2);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (11, 'b5', '登陆艇', 2);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (12, 'b6', '登陆艇', 2);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (13, 'c1', '手枪', 3);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (14, 'c2', '匕首', 3);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (15, 'c3', '对讲机', 3);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (16, 'c4', '手电筒', 3);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (17, 'c5', '头盔', 3);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (18, 'c6', '夜视镜', 3);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (19, 'd1', '睡袋', 4);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (20, 'd2', '枕头', 4);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (21, 'd3', '水壶', 4);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (22, 'd4', '保温杯', 4);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (23, 'd5', '刀具', 4);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (24, 'd6', '皮带', 4);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (25, 'e1', '火炮', 5);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (26, 'e2', '迫击炮', 5);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (27, 'e3', '重炮', 5);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (28, 'e4', '火箭炮', 5);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (29, 'e5', '地对地导弹', 5);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (30, 'e6', '洲际导弹', 5);

--装备信息(30)
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (1, 'a1', '001', '炮筒', to_date('2010-01-02 23:12:12','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '机动部', '士兵A');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (2, 'a2', '002', '履带',to_date('2010-01-03 11:13:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '机动部', '士兵B');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (3, 'a3', '003', '方向盘', to_date('2010-01-24 20:20:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '机动部', '士兵C');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (4, 'a4', '004', '瞄准器',to_date('2010-04-23 15:13:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '机动部', '士兵D');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (5, 'a5', '005', '座椅', to_date('2009-04-04 17:20:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '机动部', '士兵E');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (6, 'a6', '006', '轮子', to_date('2008-04-02 12:20:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '机动部', '士兵F');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (7, 'b1', '007', '突击车', to_date('2010-04-05 11:43:12','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '装备部', '士兵G');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (8, 'b2', '008', '空降战车',to_date('2006-04-01 11:17:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '装备部', '士兵H');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (9, 'b3', '009', '两栖战车', to_date('2007-03-24 20:28:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '装备部', '士兵I');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (10, 'b4', '010', '布雷车',to_date('2010-02-11 15:16:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '装备部', '士兵J');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (11, 'b5', '011', '登陆艇', to_date('2008-01-19 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '装备部', '士兵K');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (12, 'b6', '012', '登陆艇', to_date('2009-01-22 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '装备部', '士兵L');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (13, 'c1', '013', '手枪', to_date('2010-01-15 11:43:12','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '工兵连', '士兵M');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (14, 'c2', '014', '匕首',to_date('2007-02-16 11:17:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '工兵连', '士兵N');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (15, 'c3', '015', '对讲机', to_date('2009-02-16 20:28:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '工兵连', '士兵O');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (16, 'c4', '016', '手电筒',to_date('2010-04-05 15:16:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '工兵连', '士兵P');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (17, 'c5', '017', '头盔', to_date('2010-04-06 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '工兵连', '士兵Q');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (18, 'c6', '018', '夜视镜', to_date('2010-01-07 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '工兵连', '士兵R');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (19, 'd1', '021', '睡袋', to_date('2010-03-16 11:43:12','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '步兵团', '士兵A');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (20, 'd2', '022', '枕头',to_date('2010-03-19 11:17:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '步兵团', '士兵F');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (21, 'd3', '023', '水壶', to_date('2010-04-06 20:28:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '步兵团', '士兵J');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (22, 'd4', '024', '保温杯',to_date('2010-04-05 15:16:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '步兵团', '士兵O');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (23, 'd5', '025', '刀具', to_date('2007-04-02 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '步兵团', '士兵M');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (24, 'd6', '026', '皮带', to_date('2009-01-18 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '步兵团', '士兵T');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (25, 'e1', '031', '火炮', to_date('2006-03-11 11:43:12','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '空降兵旅', '士兵G');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (26, 'e2', '032', '迫击炮',to_date('2010-03-31 11:17:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '空降兵旅', '士兵N');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (27, 'e3', '033', '重炮', to_date('2010-04-07 20:28:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '空降兵旅', '士兵H');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (28, 'e4', '034', '火箭炮',to_date('2010-04-02 15:16:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '空降兵旅', '士兵S');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (29, 'e5', '035', '地对地导弹', to_date('2010-04-04 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '空降兵旅', '士兵M');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (30, 'e6', '036', '洲际导弹', to_date('2010-01-21 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '空降兵旅', '士兵C');

--故障信息(100)
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120103081032, 1, 'oh, no', to_date('2012-01-03 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 1, '特殊部位', '海盗甲', '010-11111111', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 1);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120115081032, 1, 'oh, no', to_date('2012-01-15 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 2, '特殊部位', '海盗甲', '010-11111111', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 1);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120123081032, 2, 'oh, no', to_date('2012-01-23 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 3, '特殊部位', '海盗甲', '010-11111111', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 1);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120127081032, 2, 'oh, no', to_date('2012-01-27 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 1);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120103081032, 2, 'oh, no', to_date('2012-01-03 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 1);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120115081032, 1, 'oh, no', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 3, '特殊部位', '海盗乙', '010-22222222', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 2);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120123081032, 2, 'oh, no', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 2, '特殊部位', '海盗乙', '010-22222222', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 2);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120127081032, 1, 'oh, no', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 2);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101220120103081032, 3, 'oh, no', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 2);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101220120115081032, 3, 'oh, no', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 2);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101220120123081032, 1, 'oh, no', to_date('2012-01-23 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 1, '特殊部位', '海盗甲', '010-11111111', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 3);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120127081032, 1, 'oh, no', to_date('2012-01-27 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 2, '特殊部位', '海盗甲', '010-11111111', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 3);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120103081032, 2, 'oh, no', to_date('2012-01-03 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 3, '特殊部位', '海盗甲', '010-11111111', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 3);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120115081032, 2, 'oh, no', to_date('2012-01-15 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 3);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120123081032, 2, 'oh, no', to_date('2012-01-23 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 3);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101320120127081032, 1, 'oh, no', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 3, '特殊部位', '海盗乙', '010-22222222', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 4);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120103081032, 2, 'oh, no', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 2, '特殊部位', '海盗乙', '010-22222222', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 4);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120115081032, 1, 'oh, no', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 4);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120123081032, 3, 'oh, no', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 4);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120127081032, 3, 'oh, no', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 4);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120208081032, 1, 'oh, no', to_date('2012-02-08 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 1, '特殊部位', '海盗甲', '010-11111111', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 5);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120221081032, 1, 'oh, no', to_date('2012-02-21 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 2, '特殊部位', '海盗甲', '010-11111111', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 5);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120208081032, 2, 'oh, no', to_date('2012-02-08 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 3, '特殊部位', '海盗甲', '010-11111111', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 5);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120221081032, 2, 'oh, no', to_date('2012-02-21 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 5);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120208081032, 2, 'oh, no', to_date('2012-02-08 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 5);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101220120221081032, 1, 'oh, no', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 3, '特殊部位', '海盗乙', '010-22222222', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 6);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120208081032, 2, 'oh, no', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 2, '特殊部位', '海盗乙', '010-22222222', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 6);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120221081032, 1, 'oh, no', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 6);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120208081032, 3, 'oh, no', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 6);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120221081032, 3, 'oh, no', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 6);
---------
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120302081032, 1, 'oh, no', to_date('2012-03-02 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 1, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 7);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120308081032, 1, 'oh, no', to_date('2012-03-08 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 2, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 7);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120312081032, 2, 'oh, no', to_date('2012-03-12 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 3, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 7);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120314081032, 2, 'oh, no', to_date('2012-03-14 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-14 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 7);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120316081032, 2, 'oh, no', to_date('2012-03-16 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 7);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120318081032, 1, 'oh, no', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 3, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 8);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101020120324081032, 2, 'oh, no', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 2, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 8);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101020120326081032, 1, 'oh, no', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 8);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101120120302081032, 3, 'oh, no', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 8);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101120120308081032, 3, 'oh, no', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 8);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120312081032, 1, 'oh, no', to_date('2012-03-12 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 1, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 9);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120314081032, 1, 'oh, no', to_date('2012-03-14 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 2, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-14 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 9);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120316081032, 2, 'oh, no', to_date('2012-03-16 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 3, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 9);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120318081032, 2, 'oh, no', to_date('2012-03-18 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 9);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120324081032, 2, 'oh, no', to_date('2012-03-24 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 9);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120326081032, 1, 'oh, no', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 3, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 10);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101220120302081032, 2, 'oh, no', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 2, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 10);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101220120308081032, 1, 'oh, no', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 10);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101220120312081032, 3, 'oh, no', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 10);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101220120314081032, 3, 'oh, no', to_date('2012-03-14 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-14 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 10);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101220120316081032, 1, 'oh, no', to_date('2012-03-16 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 1, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 11);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120318081032, 1, 'oh, no', to_date('2012-03-18 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 2, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 11);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120324081032, 2, 'oh, no', to_date('2012-03-24 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 3, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 11);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120326081032, 2, 'oh, no', to_date('2012-03-26 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 11);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120302081032, 2, 'oh, no', to_date('2012-03-02 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 11);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101320120308081032, 1, 'oh, no', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 3, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 12);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120312081032, 2, 'oh, no', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 2, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 12);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120314081032, 1, 'oh, no', to_date('2012-03-14 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-14 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 12);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120316081032, 3, 'oh, no', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 12);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120318081032, 3, 'oh, no', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 12);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101320120324081032, 1, 'oh, no', to_date('2012-03-24 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 1, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 13);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120326081032, 1, 'oh, no', to_date('2012-03-26 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 2, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 13);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101420120302081032, 2, 'oh, no', to_date('2012-03-02 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 3, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 13);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101420120308081032, 2, 'oh, no', to_date('2012-03-08 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 13);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101420120312081032, 2, 'oh, no', to_date('2012-03-12 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 13);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101420120314081032, 1, 'oh, no', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 3, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 14);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120316081032, 2, 'oh, no', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 2, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 14);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120318081032, 1, 'oh, no', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 14);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120324081032, 3, 'oh, no', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 14);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120326081032, 3, 'oh, no', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 14);
----------------------
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120403081032, 1, 'oh, no', to_date('2012-04-03 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 1, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 15);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120407081032, 1, 'oh, no', to_date('2012-04-07 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 2, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 15);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120413081032, 2, 'oh, no', to_date('2012-04-13 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 3, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 15);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120417081032, 2, 'oh, no', to_date('2012-04-17 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 15);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120423081032, 2, 'oh, no', to_date('2012-04-23 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 15);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120427081032, 1, 'oh, no', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 3, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 16);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101120120403081032, 2, 'oh, no', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 2, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 16);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101120120407081032, 1, 'oh, no', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 16);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101120120413081032, 3, 'oh, no', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 16);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101120120417081032, 3, 'oh, no', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 16);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120423081032, 1, 'oh, no', to_date('2012-04-23 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 1, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 17);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120427081032, 1, 'oh, no', to_date('2012-04-27 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 2, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 17);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120403081032, 2, 'oh, no', to_date('2012-04-03 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 3, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 17);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120407081032, 2, 'oh, no', to_date('2012-04-07 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 17);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120413081032, 2, 'oh, no', to_date('2012-04-13 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 17);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101220120417081032, 1, 'oh, no', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 3, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 18);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101220120423081032, 2, 'oh, no', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 2, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 18);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101220120427081032, 1, 'oh, no', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 18);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120403081032, 3, 'oh, no', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 18);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120407081032, 3, 'oh, no', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 18);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101320120413081032, 1, 'oh, no', to_date('2012-04-13 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 1, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 19);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120417081032, 1, 'oh, no', to_date('2012-04-17 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 2, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 19);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120423081032, 2, 'oh, no', to_date('2012-04-23 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 3, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 19);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120427081032, 2, 'oh, no', to_date('2012-04-27 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 19);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101420120403081032, 2, 'oh, no', to_date('2012-04-03 08:10:32','yyyy-mm-dd   hh24:mi:ss'), '啥都没做', 4, '特殊部位', '海盗甲', '010-11111111', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 19);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101420120407081032, 1, 'oh, no', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 3, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 20);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120413081032, 2, 'oh, no', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 2, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 20);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120417081032, 1, 'oh, no', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 20);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120423081032, 3, 'oh, no', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 20);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120427081032, 3, 'oh, no', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '不知道呀', 1, '特殊部位', '海盗乙', '010-22222222', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 20);

--审核信息(100)
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (1, 'regiment', null, to_date('2012-01-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 101020120103081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (2, 'regiment', null, to_date('2012-01-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120102081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (3, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120103081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (4, 'regiment', null, to_date('2012-02-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120104081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (5, 'regiment', null, to_date('2012-02-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120105081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (6, 'regiment', null, to_date('2012-03-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120111081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (7, 'regiment', null, to_date('2012-03-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120114081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (8, 'regiment', null, to_date('2012-03-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120115081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (9, 'regiment', null, to_date('2012-03-14 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120117081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (10, 'regiment', null, to_date('2012-04-01 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120118081032, 3);

insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (11, 'regiment', null, to_date('2012-01-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120120081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (12, 'regiment', null, to_date('2012-01-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120121081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (13, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120123081032，3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (14, 'regiment', null, to_date('2012-02-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120122081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (15, 'regiment', null, to_date('2012-02-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120124081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (16, 'regiment', null, to_date('2012-03-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120125081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (17, 'regiment', null, to_date('2012-03-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120126081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (18, 'regiment', null, to_date('2012-03-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120127081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (19, 'regiment', null, to_date('2012-03-14 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120128081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (20, 'regiment', null, to_date('2012-04-01 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120131081032, 3);

insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (21, 'regiment', null, to_date('2012-01-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120201081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (22, 'regiment', null, to_date('2012-01-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120202081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (23, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120205081032，3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (24, 'regiment', null, to_date('2012-02-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120206081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (25, 'regiment', null, to_date('2012-02-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120208081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (26, 'regiment', null, to_date('2012-03-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120210081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (27, 'regiment', null, to_date('2012-03-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120211081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (28, 'regiment', null, to_date('2012-03-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120213081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (29, 'regiment', null, to_date('2012-03-14 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120215081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (30, 'regiment', null, to_date('2012-04-01 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120218081032, 3);

insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (31, 'regiment', null, to_date('2012-01-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120220081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (32, 'regiment', null, to_date('2012-01-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120221081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (33, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120222081032，3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (34, 'regiment', null, to_date('2012-02-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120225081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (35, 'regiment', null, to_date('2012-02-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120223081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (36, 'regiment', null, to_date('2012-03-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120224081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (37, 'regiment', null, to_date('2012-03-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120226081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (38, 'regiment', null, to_date('2012-03-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120228081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (39, 'regiment', null, to_date('2012-03-14 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120227081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (40, 'regiment', null, to_date('2012-04-01 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120229081032, 3);

insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (41, 'regiment', null, to_date('2012-01-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120301081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (42, 'regiment', null, to_date('2012-01-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120301081532, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (43, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120301081932，3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (44, 'regiment', null, to_date('2012-02-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120302191032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (45, 'regiment', null, to_date('2012-02-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120301201032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (46, 'regiment', null, to_date('2012-03-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120302081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (47, 'regiment', null, to_date('2012-03-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120302111032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (48, 'regiment', null, to_date('2012-03-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120302151032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (49, 'regiment', null, to_date('2012-03-14 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120302211032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (50, 'regiment', null, to_date('2012-04-01 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120302232632, 3);

insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (51, 'regiment', null, to_date('2012-01-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120305081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (52, 'regiment', null, to_date('2012-01-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120305171032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (53, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120305214332，3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (54, 'regiment', null, to_date('2012-02-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120306131032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (55, 'regiment', null, to_date('2012-02-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120306152532, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (56, 'regiment', null, to_date('2012-03-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120306211032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (57, 'regiment', null, to_date('2012-03-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120306214232, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (58, 'regiment', null, to_date('2012-03-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120306223532, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (59, 'regiment', null, to_date('2012-03-14 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120306233126, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (60, 'regiment', null, to_date('2012-04-01 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120306234332, 3);

insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (61, 'regiment', null, to_date('2012-01-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120307081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (62, 'regiment', null, to_date('2012-01-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120307091132, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (63, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120307101532，3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (64, 'regiment', null, to_date('2012-02-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120308131032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (65, 'regiment', null, to_date('2012-02-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120308191032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (66, 'regiment', null, to_date('2012-03-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120308211032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (67, 'regiment', null, to_date('2012-03-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120309111032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (68, 'regiment', null, to_date('2012-03-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120309151032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (69, 'regiment', null, to_date('2012-03-14 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120309164232, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (70, 'regiment', null, to_date('2012-04-01 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120309211032, 3);

insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (71, 'regiment', null, to_date('2012-01-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120311081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (72, 'regiment', null, to_date('2012-01-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120311211032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (73, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120312091032，3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (74, 'regiment', null, to_date('2012-02-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120312151032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (75, 'regiment', null, to_date('2012-02-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120312181032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (76, 'regiment', null, to_date('2012-03-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120313121032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (77, 'regiment', null, to_date('2012-03-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120313111032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (78, 'regiment', null, to_date('2012-03-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120313221032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (79, 'regiment', null, to_date('2012-03-14 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120315141032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (80, 'regiment', null, to_date('2012-04-01 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120315083232, 3);

insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (81, 'regiment', null, to_date('2012-01-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120317211032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (82, 'regiment', null, to_date('2012-01-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120317223232, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (83, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120318101032，3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (84, 'regiment', null, to_date('2012-02-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120318153132, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (85, 'regiment', null, to_date('2012-02-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120319091032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (86, 'regiment', null, to_date('2012-03-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120319124332, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (87, 'regiment', null, to_date('2012-03-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120319141032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (88, 'regiment', null, to_date('2012-03-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120320081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (89, 'regiment', null, to_date('2012-03-14 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120321081032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (90, 'regiment', null, to_date('2012-04-01 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120322161032, 3);

insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (91, 'regiment', null, to_date('2012-01-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120323101032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (92, 'regiment', null, to_date('2012-01-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120323161732, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (93, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120324153032，3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (94, 'regiment', null, to_date('2012-02-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120325101032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (95, 'regiment', null, to_date('2012-02-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120326101032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (96, 'regiment', null, to_date('2012-03-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120327101032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (97, 'regiment', null, to_date('2012-03-12 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120328101032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (98, 'regiment', null, to_date('2012-03-13 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120329101032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (99, 'regiment', null, to_date('2012-03-14 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120330101032, 3);
insert into FRSS_APPROVAL_INFO (ID, CHECKER, OPINION, CHECKTIME, TYPE, STATUS, KEYID, USERID)
values (100, 'regiment', null, to_date('2012-04-01 10:28:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120331101032, 3);