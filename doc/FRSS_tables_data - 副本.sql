--�û���Ϣ��
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (1, 'super', 'Gzt3XR0PAto=', 0, 'super', 0, 'nothing', 'nothing', null, 'mc');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (2, 'f1', 'Gzt3XR0PAto=', 6, '����', 0, '����', '��ҵ����', 'no', 'super');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (3, 'e1', 'Gzt3XR0PAto=', 5, 'ר��', 0, 'ר��', 'ѧԺ', 'no', 'super');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (4, 'f1', 'Gzt3XR0PAto=', 7, '��ʽ���Ա', 3, '��ʽ���Ա', 'ά������', 'no', 'super');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (5, 'd1', 'Gzt3XR0PAto=', 4, '�ַ�Ա', 7, '�ַ�Ա', 'ά������', 'no', 'super');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (6, 'm1', 'Gzt3XR0PAto=', 3, '����A', 2, 'm1', '����A', 'no', 'f1');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (7, 'm2', 'Gzt3XR0PAto=', 3, '����B', 2, 'm2', '����B', 'no', 'f1');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (8, 'g1', 'Gzt3XR0PAto=', 2, '���ž�A', 1, '���ž�A', '���ž�A', 'no', 'm1');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (9, 'g2', 'Gzt3XR0PAto=', 2, '���ž�B', 1, '���ž�B', '���ž�B', 'no', 'm2');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (10, 'r1', 'Gzt3XR0PAto=', 1, '��A', 0, '��A', '��A', 'no', 'g1');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (11, 'r2', 'Gzt3XR0PAto=', 1, '��B', 0, '��B', '��B', 'no', 'g1');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (12, 'r3', 'Gzt3XR0PAto=', 1, '��C', 0, '��C', '��C', 'no', 'g1');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (13, 'r4', 'Gzt3XR0PAto=', 1, '��D', 0, '��D', '��D', 'no', 'g2');
insert into FRSS_USER_INFO (ID, USERNAME, MD5PASSWORD, USERTYPE, FULLNAME, SUBTYPE, DESCRIPTION, DEPARTMENT, EMAIL, CREATEMAN)
values (14, 'r5', 'Gzt3XR0PAto=', 1, '��E', 0, '��E', '��E', 'no', 'g2');

--��ҵ������Ϣ(5)
insert into FRSS_FACTORY_INFO (ID, NAME, ADDRESS, CODE, RANGE, GUARANTEE, GUARDADDRESS, ABILITY, CONTACT, CONTACTWAY, MACHINIST)
values (1, '����1', '����·��1��', 'f1', 'װ�׳�', '����ʦ01', '����·2��', '5', '����A', '010-12345678', '����ʦA');
insert into FRSS_FACTORY_INFO (ID, NAME, ADDRESS, CODE, RANGE, GUARANTEE, GUARDADDRESS, ABILITY, CONTACT, CONTACTWAY, MACHINIST)
values (2, '����2', '�Ͼ�·2��', 'f2', '��������', '������01', '�껨̨1��', '4', '����B', '025-87654321', '����ʦB');
insert into FRSS_FACTORY_INFO (ID, NAME, ADDRESS, CODE, RANGE, GUARANTEE, GUARDADDRESS, ABILITY, CONTACT, CONTACTWAY, MACHINIST)
values (3, '����3', '�人·3��', 'f3', '��Я����', '����01', '��ʨ��·5��', '3', '����C', '027-12348765', '����ʦC');
insert into FRSS_FACTORY_INFO (ID, NAME, ADDRESS, CODE, RANGE, GUARANTEE, GUARDADDRESS, ABILITY, CONTACT, CONTACTWAY, MACHINIST)
values (4, '����4', '����·7��', 'f4', '����װ��', 'Ұս��01', '�ؽ�·4��', '3', '����D', '023-67548765', '����ʦD');
insert into FRSS_FACTORY_INFO (ID, NAME, ADDRESS, CODE, RANGE, GUARANTEE, GUARDADDRESS, ABILITY, CONTACT, CONTACTWAY, MACHINIST)
values (5, '����5', '�ƺ�·9��', 'f5', '����', '�ڱ���02', '�ٺ�·8��', '3', '����E', '029-67548765', '����ʦE');

--װ��������Ϣ��(30)
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (1, 'a1', '��Ͳ', 1);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (2, 'a2', '�Ĵ�', 1);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (3, 'a3', '������', 1);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (4, 'a4', '��׼��', 1);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (5, 'a5', '����', 1);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (6, 'a6', '����', 1);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (7, 'b1', 'ͻ����', 2);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (8, 'b2', '�ս�ս��', 2);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (9, 'b3', '����ս��', 2);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (10, 'b4', '���׳�', 2);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (11, 'b5', '��½ͧ', 2);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (12, 'b6', '��½ͧ', 2);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (13, 'c1', '��ǹ', 3);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (14, 'c2', 'ذ��', 3);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (15, 'c3', '�Խ���', 3);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (16, 'c4', '�ֵ�Ͳ', 3);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (17, 'c5', 'ͷ��', 3);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (18, 'c6', 'ҹ�Ӿ�', 3);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (19, 'd1', '˯��', 4);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (20, 'd2', '��ͷ', 4);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (21, 'd3', 'ˮ��', 4);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (22, 'd4', '���±�', 4);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (23, 'd5', '����', 4);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (24, 'd6', 'Ƥ��', 4);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (25, 'e1', '����', 5);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (26, 'e2', '�Ȼ���', 5);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (27, 'e3', '����', 5);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (28, 'e4', '�����', 5);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (29, 'e5', '�ضԵص���', 5);
insert into FRSS_EQUIP_CREATING (ID, EQUIPTYPE, EQUIPNAME, FACTORYID)
values (30, 'e6', '�޼ʵ���', 5);

--װ����Ϣ(30)
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (1, 'a1', '001', '��Ͳ', to_date('2010-01-02 23:12:12','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '������', 'ʿ��A');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (2, 'a2', '002', '�Ĵ�',to_date('2010-01-03 11:13:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '������', 'ʿ��B');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (3, 'a3', '003', '������', to_date('2010-01-24 20:20:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '������', 'ʿ��C');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (4, 'a4', '004', '��׼��',to_date('2010-04-23 15:13:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '������', 'ʿ��D');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (5, 'a5', '005', '����', to_date('2009-04-04 17:20:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '������', 'ʿ��E');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (6, 'a6', '006', '����', to_date('2008-04-02 12:20:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '������', 'ʿ��F');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (7, 'b1', '007', 'ͻ����', to_date('2010-04-05 11:43:12','yyyy-mm-dd   hh24:mi:ss '), 'nothing', 'װ����', 'ʿ��G');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (8, 'b2', '008', '�ս�ս��',to_date('2006-04-01 11:17:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', 'װ����', 'ʿ��H');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (9, 'b3', '009', '����ս��', to_date('2007-03-24 20:28:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', 'װ����', 'ʿ��I');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (10, 'b4', '010', '���׳�',to_date('2010-02-11 15:16:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', 'װ����', 'ʿ��J');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (11, 'b5', '011', '��½ͧ', to_date('2008-01-19 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', 'װ����', 'ʿ��K');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (12, 'b6', '012', '��½ͧ', to_date('2009-01-22 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', 'װ����', 'ʿ��L');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (13, 'c1', '013', '��ǹ', to_date('2010-01-15 11:43:12','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '������', 'ʿ��M');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (14, 'c2', '014', 'ذ��',to_date('2007-02-16 11:17:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '������', 'ʿ��N');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (15, 'c3', '015', '�Խ���', to_date('2009-02-16 20:28:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '������', 'ʿ��O');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (16, 'c4', '016', '�ֵ�Ͳ',to_date('2010-04-05 15:16:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '������', 'ʿ��P');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (17, 'c5', '017', 'ͷ��', to_date('2010-04-06 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '������', 'ʿ��Q');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (18, 'c6', '018', 'ҹ�Ӿ�', to_date('2010-01-07 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '������', 'ʿ��R');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (19, 'd1', '021', '˯��', to_date('2010-03-16 11:43:12','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '������', 'ʿ��A');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (20, 'd2', '022', '��ͷ',to_date('2010-03-19 11:17:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '������', 'ʿ��F');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (21, 'd3', '023', 'ˮ��', to_date('2010-04-06 20:28:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '������', 'ʿ��J');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (22, 'd4', '024', '���±�',to_date('2010-04-05 15:16:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '������', 'ʿ��O');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (23, 'd5', '025', '����', to_date('2007-04-02 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '������', 'ʿ��M');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (24, 'd6', '026', 'Ƥ��', to_date('2009-01-18 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '������', 'ʿ��T');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (25, 'e1', '031', '����', to_date('2006-03-11 11:43:12','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '�ս�����', 'ʿ��G');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (26, 'e2', '032', '�Ȼ���',to_date('2010-03-31 11:17:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '�ս�����', 'ʿ��N');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (27, 'e3', '033', '����', to_date('2010-04-07 20:28:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '�ս�����', 'ʿ��H');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (28, 'e4', '034', '�����',to_date('2010-04-02 15:16:20','yyyy-mm-dd   hh24:mi:ss '), 'nothing', '�ս�����', 'ʿ��S');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (29, 'e5', '035', '�ضԵص���', to_date('2010-04-04 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '�ս�����', 'ʿ��M');
insert into FRSS_EQUIPMENT_INFO (ID, EQUIPTYPE, EQUIPNUMBER, EQUIPNAME, CREATETIME, DESCRIPTION, DEPARTMENT, OPERATER)
values (30, 'e6', '036', '�޼ʵ���', to_date('2010-01-21 17:50:20', 'yyyy-mm-dd   hh24:mi:ss'), 'nothing', '�ս�����', 'ʿ��C');

--������Ϣ(100)
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120103081032, 1, 'oh, no', to_date('2012-01-03 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 1, '���ⲿλ', '������', '010-11111111', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 1);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120115081032, 1, 'oh, no', to_date('2012-01-15 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 2, '���ⲿλ', '������', '010-11111111', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 1);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120123081032, 2, 'oh, no', to_date('2012-01-23 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 3, '���ⲿλ', '������', '010-11111111', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 1);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120127081032, 2, 'oh, no', to_date('2012-01-27 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 1);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120103081032, 2, 'oh, no', to_date('2012-01-03 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 1);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120115081032, 1, 'oh, no', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 3, '���ⲿλ', '������', '010-22222222', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 2);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120123081032, 2, 'oh, no', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 2, '���ⲿλ', '������', '010-22222222', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 2);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120127081032, 1, 'oh, no', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 2);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101220120103081032, 3, 'oh, no', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 2);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101220120115081032, 3, 'oh, no', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 2);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101220120123081032, 1, 'oh, no', to_date('2012-01-23 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 1, '���ⲿλ', '������', '010-11111111', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 3);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120127081032, 1, 'oh, no', to_date('2012-01-27 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 2, '���ⲿλ', '������', '010-11111111', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 3);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120103081032, 2, 'oh, no', to_date('2012-01-03 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 3, '���ⲿλ', '������', '010-11111111', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 3);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120115081032, 2, 'oh, no', to_date('2012-01-15 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 3);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120123081032, 2, 'oh, no', to_date('2012-01-23 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 3);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101320120127081032, 1, 'oh, no', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 3, '���ⲿλ', '������', '010-22222222', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 4);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120103081032, 2, 'oh, no', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 2, '���ⲿλ', '������', '010-22222222', to_date('2012-01-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 4);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120115081032, 1, 'oh, no', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-01-15 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 4);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120123081032, 3, 'oh, no', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-01-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 4);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120127081032, 3, 'oh, no', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-01-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 4);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120208081032, 1, 'oh, no', to_date('2012-02-08 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 1, '���ⲿλ', '������', '010-11111111', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 5);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120221081032, 1, 'oh, no', to_date('2012-02-21 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 2, '���ⲿλ', '������', '010-11111111', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 5);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120208081032, 2, 'oh, no', to_date('2012-02-08 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 3, '���ⲿλ', '������', '010-11111111', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 5);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120221081032, 2, 'oh, no', to_date('2012-02-21 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 5);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120208081032, 2, 'oh, no', to_date('2012-02-08 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 5);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101220120221081032, 1, 'oh, no', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 3, '���ⲿλ', '������', '010-22222222', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 6);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120208081032, 2, 'oh, no', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 2, '���ⲿλ', '������', '010-22222222', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 6);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120221081032, 1, 'oh, no', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 6);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120208081032, 3, 'oh, no', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-02-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 6);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120221081032, 3, 'oh, no', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-02-21 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 6);
---------
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120302081032, 1, 'oh, no', to_date('2012-03-02 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 1, '���ⲿλ', '������', '010-11111111', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 7);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120308081032, 1, 'oh, no', to_date('2012-03-08 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 2, '���ⲿλ', '������', '010-11111111', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 7);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120312081032, 2, 'oh, no', to_date('2012-03-12 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 3, '���ⲿλ', '������', '010-11111111', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 7);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120314081032, 2, 'oh, no', to_date('2012-03-14 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-03-14 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 7);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120316081032, 2, 'oh, no', to_date('2012-03-16 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 7);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120318081032, 1, 'oh, no', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 3, '���ⲿλ', '������', '010-22222222', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 8);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101020120324081032, 2, 'oh, no', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 2, '���ⲿλ', '������', '010-22222222', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 8);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101020120326081032, 1, 'oh, no', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 8);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101120120302081032, 3, 'oh, no', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 8);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101120120308081032, 3, 'oh, no', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 8);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120312081032, 1, 'oh, no', to_date('2012-03-12 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 1, '���ⲿλ', '������', '010-11111111', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 9);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120314081032, 1, 'oh, no', to_date('2012-03-14 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 2, '���ⲿλ', '������', '010-11111111', to_date('2012-03-14 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 9);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120316081032, 2, 'oh, no', to_date('2012-03-16 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 3, '���ⲿλ', '������', '010-11111111', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 9);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120318081032, 2, 'oh, no', to_date('2012-03-18 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 9);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120324081032, 2, 'oh, no', to_date('2012-03-24 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 9);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120326081032, 1, 'oh, no', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 3, '���ⲿλ', '������', '010-22222222', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 10);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101220120302081032, 2, 'oh, no', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 2, '���ⲿλ', '������', '010-22222222', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 10);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101220120308081032, 1, 'oh, no', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 10);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101220120312081032, 3, 'oh, no', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 10);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101220120314081032, 3, 'oh, no', to_date('2012-03-14 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-03-14 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 10);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101220120316081032, 1, 'oh, no', to_date('2012-03-16 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 1, '���ⲿλ', '������', '010-11111111', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 11);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120318081032, 1, 'oh, no', to_date('2012-03-18 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 2, '���ⲿλ', '������', '010-11111111', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 11);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120324081032, 2, 'oh, no', to_date('2012-03-24 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 3, '���ⲿλ', '������', '010-11111111', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 11);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120326081032, 2, 'oh, no', to_date('2012-03-26 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 11);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120302081032, 2, 'oh, no', to_date('2012-03-02 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 11);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101320120308081032, 1, 'oh, no', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 3, '���ⲿλ', '������', '010-22222222', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 12);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120312081032, 2, 'oh, no', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 2, '���ⲿλ', '������', '010-22222222', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 12);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120314081032, 1, 'oh, no', to_date('2012-03-14 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-03-14 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 12);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120316081032, 3, 'oh, no', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 12);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120318081032, 3, 'oh, no', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 12);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101320120324081032, 1, 'oh, no', to_date('2012-03-24 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 1, '���ⲿλ', '������', '010-11111111', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 13);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120326081032, 1, 'oh, no', to_date('2012-03-26 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 2, '���ⲿλ', '������', '010-11111111', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 13);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101420120302081032, 2, 'oh, no', to_date('2012-03-02 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 3, '���ⲿλ', '������', '010-11111111', to_date('2012-03-02 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 13);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101420120308081032, 2, 'oh, no', to_date('2012-03-08 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-03-08 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 13);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101420120312081032, 2, 'oh, no', to_date('2012-03-12 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-03-12 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 13);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101420120314081032, 1, 'oh, no', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 3, '���ⲿλ', '������', '010-22222222', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 14);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120316081032, 2, 'oh, no', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 2, '���ⲿλ', '������', '010-22222222', to_date('2012-03-16 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 14);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120318081032, 1, 'oh, no', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-03-18 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 14);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120324081032, 3, 'oh, no', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-03-24 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 14);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120326081032, 3, 'oh, no', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-03-26 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 14);
----------------------
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120403081032, 1, 'oh, no', to_date('2012-04-03 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 1, '���ⲿλ', '������', '010-11111111', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 15);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120407081032, 1, 'oh, no', to_date('2012-04-07 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 2, '���ⲿλ', '������', '010-11111111', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 15);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120413081032, 2, 'oh, no', to_date('2012-04-13 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 3, '���ⲿλ', '������', '010-11111111', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 15);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120417081032, 2, 'oh, no', to_date('2012-04-17 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 15);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101020120423081032, 2, 'oh, no', to_date('2012-04-23 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 15);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101020120427081032, 1, 'oh, no', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 3, '���ⲿλ', '������', '010-22222222', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 16);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101120120403081032, 2, 'oh, no', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 2, '���ⲿλ', '������', '010-22222222', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 16);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101120120407081032, 1, 'oh, no', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 16);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101120120413081032, 3, 'oh, no', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 16);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101120120417081032, 3, 'oh, no', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 16);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101120120423081032, 1, 'oh, no', to_date('2012-04-23 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 1, '���ⲿλ', '������', '010-11111111', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 17);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101120120427081032, 1, 'oh, no', to_date('2012-04-27 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 2, '���ⲿλ', '������', '010-11111111', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 17);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120403081032, 2, 'oh, no', to_date('2012-04-03 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 3, '���ⲿλ', '������', '010-11111111', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 17);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120407081032, 2, 'oh, no', to_date('2012-04-07 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 17);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101220120413081032, 2, 'oh, no', to_date('2012-04-13 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 17);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101220120417081032, 1, 'oh, no', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 3, '���ⲿλ', '������', '010-22222222', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 18);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101220120423081032, 2, 'oh, no', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 2, '���ⲿλ', '������', '010-22222222', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 18);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101220120427081032, 1, 'oh, no', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 18);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120403081032, 3, 'oh, no', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 18);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101320120407081032, 3, 'oh, no', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 18);

insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101320120413081032, 1, 'oh, no', to_date('2012-04-13 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 1, '���ⲿλ', '������', '010-11111111', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 19);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120417081032, 1, 'oh, no', to_date('2012-04-17 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 2, '���ⲿλ', '������', '010-11111111', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 19);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120423081032, 2, 'oh, no', to_date('2012-04-23 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 3, '���ⲿλ', '������', '010-11111111', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 19);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101320120427081032, 2, 'oh, no', to_date('2012-04-27 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 19);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                    
values (101420120403081032, 2, 'oh, no', to_date('2012-04-03 08:10:32','yyyy-mm-dd   hh24:mi:ss'), 'ɶ��û��', 4, '���ⲿλ', '������', '010-11111111', to_date('2012-04-03 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 19);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)
values (101420120407081032, 1, 'oh, no', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 3, '���ⲿλ', '������', '010-22222222', to_date('2012-04-07 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 20);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120413081032, 2, 'oh, no', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 2, '���ⲿλ', '������', '010-22222222', to_date('2012-04-13 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 20);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120417081032, 1, 'oh, no', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-04-17 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 20);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120423081032, 3, 'oh, no', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-04-23 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 20);
insert into FRSS_FAULT_INFO (ID, AMOUNT, FAULTDESP, FAULTTIME, PREPROCESS, FREQUENCY, FAULTPLACE, REPORTER, CONTACTWAY, REPORTTIME, PHOTOS, CAUSE, EQUIPID)                                                         
values (101420120427081032, 3, 'oh, no', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), '��֪��ѽ', 1, '���ⲿλ', '������', '010-22222222', to_date('2012-04-27 08:10:32', 'yyyy-mm-dd   hh24:mi:ss'), null, null, 20);

--�����Ϣ(100)
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
values (13, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120123081032��3);
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
values (23, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120205081032��3);
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
values (33, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120222081032��3);
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
values (43, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120301081932��3);
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
values (53, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120305214332��3);
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
values (63, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120307101532��3);
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
values (73, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120312091032��3);
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
values (83, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120318101032��3);
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
values (93, 'regiment', null, to_date('2012-02-11 10:20:15','yyyy-mm-dd   hh24:mi:ss'), 1, 0, 100320120324153032��3);
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