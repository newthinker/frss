CREATE TABLE FRSS_FACTORY_INFO (
  ID DECIMAL(20) NOT NULL,
  NAME VARCHAR2(32) NOT NULL,
  ADDRESS VARCHAR2(256) NOT NULL,
  CODE VARCHAR2(256),
  RANGE VARCHAR2(256),
  GUARANTEE VARCHAR2(256) ,
  GUARDADDRESS VARCHAR2(256) ,
  ABILITY NUMBER(1),
  CONTACT VARCHAR2(32) ,
  CONTACTWAY VARCHAR2(256),
  MACHINIST VARCHAR2(32)
)
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6
    minextents 1
    maxextents unlimited
  );
comment on table FRSS_FACTORY_INFO
  is '��ҵ������Ϣ����¼��ҵ���ŵ������Ϣ��';
comment on column FRSS_FACTORY_INFO.ID
  is '��ҵ���ŵı�ţ����������Ը��ݱ����װ����Ϣ���в����ѳ���װ���ͺ�';
comment on column FRSS_FACTORY_INFO.NAME
  is '��������';
comment on column FRSS_FACTORY_INFO.ADDRESS
  is '������ַ';
comment on column FRSS_FACTORY_INFO.CODE
  is '��������';
comment on column FRSS_FACTORY_INFO.RANGE
  is '������Χ';
comment on column FRSS_FACTORY_INFO.GUARANTEE
  is '���䲿������';
comment on column FRSS_FACTORY_INFO.GUARDADDRESS
  is '���䲿�ӵ�ַ';
comment on column FRSS_FACTORY_INFO.ABILITY
  is '��������������Ŀǰ�ݷ�1��5�����ӵ͵��ߣ�Ĭ��Ϊ1.';
comment on column FRSS_FACTORY_INFO.CONTACT
  is '������ϵ������';
comment on column FRSS_FACTORY_INFO.CONTACTWAY
  is '������ϵ�˵���ϵ��ʽ';
comment on column FRSS_FACTORY_INFO.MACHINIST
  is 'ά����Ա����';
alter table FRSS_FACTORY_INFO add constraint FRSS_FACTORY_INFO_PK primary key (ID);  

CREATE TABLE FRSS_EQUIPMENT_INFO (
  ID DECIMAL(20) NOT NULL  ,
  EQUIPTYPE VARCHAR2(256) NOT NULL,
  EQUIPNUMBER VARCHAR2(256) NOT NULL,
  EQUIPNAME VARCHAR2(256) NOT NULL,
  CREATETIME DATE,
  DESCRIPTION VARCHAR2(256),
  DEPARTMENT VARCHAR2(256),
  OPERATER VARCHAR2(32)
) 
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6
    minextents 1
    maxextents unlimited
  ); 
comment on table FRSS_EQUIPMENT_INFO 
  is 'װ����Ϣ����¼����װ���Ļ�����Ϣ��';
comment on column FRSS_EQUIPMENT_INFO.ID
  is 'װ������ţ���������';
comment on column FRSS_EQUIPMENT_INFO.EQUIPTYPE
  is 'װ���ͺ�';
comment on column FRSS_EQUIPMENT_INFO.EQUIPNUMBER
  is 'װ�����';
comment on column FRSS_EQUIPMENT_INFO.EQUIPNAME
  is 'װ������';
comment on column FRSS_EQUIPMENT_INFO.CREATETIME
  is 'װ������ʱ��';
comment on column FRSS_EQUIPMENT_INFO.DESCRIPTION
  is 'װ��������Ϣ';
comment on column FRSS_EQUIPMENT_INFO.DEPARTMENT
  is 'װ��ʹ�õ�λ';
comment on column FRSS_EQUIPMENT_INFO.OPERATER
  is 'װ��������';
alter table FRSS_EQUIPMENT_INFO add constraint FRSS_EQUIPMENT_INFO_PK primary key (ID);

CREATE TABLE FRSS_EQUIP_CREATING (
  ID DECIMAL(20) NOT NULL,
  EQUIPTYPE VARCHAR2(256) NOT NULL,
  EQUIPNAME VARCHAR2(256) NOT NULL,
  FACTORYID DECIMAL(20) NOT NULL
)
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6
    minextents 1
    maxextents unlimited
  );
comment on table FRSS_EQUIP_CREATING
  is 'װ��������Ϣ����¼����װ�������쵥λ����Ϣ��';
comment on column FRSS_EQUIP_CREATING.ID
  is 'װ��������Ϣ�����к�';
comment on column FRSS_EQUIP_CREATING.EQUIPTYPE
  is 'װ������';
comment on column FRSS_EQUIP_CREATING.EQUIPNAME
  is 'װ������';
comment on column FRSS_EQUIP_CREATING.FACTORYID
  is 'װ�������Ĺ�ҵ�������к�';
alter table FRSS_EQUIP_CREATING add constraint FRSS_EQUIP_CREATING_PK primary key (ID);  

CREATE TABLE FRSS_FAULT_INFO (
  ID DECIMAL(20) NOT NULL,
  AMOUNT DECIMAL(5) NOT NULL,
  FAULTDESP VARCHAR2(256),
  FAULTTIME DATE NOT NULL,
  PREPROCESS VARCHAR2(256),
  FREQUENCY DECIMAL(5),
  FAULTPLACE VARCHAR2(256) NOT NULL,
  REPORTER VARCHAR2(32) NOT NULL,
  CONTACTWAY VARCHAR2(256),
  REPORTTIME DATE,
  PHOTONAME VARCHAR2(256),
  PHOTOS BLOB,
  CAUSE VARCHAR2(256),
  EQUIPID DECIMAL(20)
) 
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6
    minextents 1
    maxextents unlimited
  );
comment on table FRSS_FAULT_INFO
  is 'װ��������Ϣ����¼����װ���ĸ��������Ϣ��';
comment on column FRSS_FAULT_INFO.ID
  is '���ϱ�ţ�Ҳ���Ե����ǹ���ά�����뵥��';
comment on column FRSS_FAULT_INFO.AMOUNT
  is '����װ������';
comment on column FRSS_FAULT_INFO.FAULTDESP
  is 'װ������������Ϣ';
comment on column FRSS_FAULT_INFO.FAULTTIME
  is '���Ϸ���ʱ��';
comment on column FRSS_FAULT_INFO.PREPROCESS
  is 'װ������ǰ�ڴ������';
comment on column FRSS_FAULT_INFO.FREQUENCY
  is 'װ�����Ϸ���Ƶ��';
comment on column FRSS_FAULT_INFO.FAULTPLACE
  is '���Ϸ�����λ';
comment on column FRSS_FAULT_INFO.REPORTER
  is '���ϼ�¼��';
comment on column FRSS_FAULT_INFO.CONTACTWAY  
  is '���ϼ�¼�ߵ���ϵ��ʽ';
comment on column FRSS_FAULT_INFO.REPORTTIME
  is '����ʱ��';
comment on column FRSS_FAULT_INFO.PHOTOS
  is 'ȡ֤��Ƭ';
comment on column FRSS_FAULT_INFO.CAUSE
  is '����ԭ��';
comment on column FRSS_FAULT_INFO.EQUIPID
  is 'װ����ţ�Ĭ��Ϊ0';  
alter table FRSS_FAULT_INFO add constraint FRSS_FAULT_INFO_PK primary key (ID);    

CREATE TABLE FRSS_BACKUP_APP (
  ID DECIMAL(20) NOT NULL,
  EQUIPLACE VARCHAR2(256),
  BACKCOUNT DECIMAL(5) NOT NULL,
  REPORTER VARCHAR2(32) NOT NULL,
  CONTACTWAY VARCHAR2(256) NOT NULL,
  REPORTTIME DATE NOT NULL,
  EQUIPID DECIMAL(20),
  FAULTID DECIMAL(20)
)
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6
    minextents 1
    maxextents unlimited
  ); 
comment on table FRSS_BACKUP_APP
  is 'װ�������������¼������װ�������������Ϣ��';
comment on column FRSS_BACKUP_APP.ID
  is 'װ���������뵥���';
comment on column FRSS_BACKUP_APP.EQUIPLACE
  is '����ʹ��װ����λ';
comment on column FRSS_BACKUP_APP.BACKCOUNT
  is '��������';
comment on column FRSS_BACKUP_APP.REPORTER
  is '���������¼��';
comment on column FRSS_BACKUP_APP.CONTACTWAY
  is '���������¼�˵���ϵ��ʽ';
comment on column FRSS_BACKUP_APP.REPORTTIME
  is '��������ʱ��';  
comment on column FRSS_BACKUP_APP.EQUIPID
  is 'װ����ţ�Ĭ��Ϊ0';
comment on column FRSS_BACKUP_APP.FAULTID
  is '���ϱ�ţ�Ĭ��Ϊ0';  
alter table FRSS_BACKUP_APP add constraint FRSS_BACKUP_APP_PK primary key (ID);
  
CREATE TABLE FRSS_APPROVAL_INFO (
  ID DECIMAL  DEFAULT NULL,
  CHECKER VARCHAR2(64),
  OPINION VARCHAR2(256),
  CHECKTIME DATE,
  TYPE NUMBER(1) NOT NULL,
  STATUS NUMBER(1) NOT NULL,
  KEYID DECIMAL(20) NOT NULL,
  USERID	DECIMAL(20)
) 
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6
    minextents 1
    maxextents unlimited
  ); 
comment on table FRSS_APPROVAL_INFO
  is '��¼�����������Ϣ�Լ�������������';
comment on column FRSS_APPROVAL_INFO.ID
  is '����߱�ţ�����';
comment on column FRSS_APPROVAL_INFO.CHECKER
  is '����������������Ҳ�Ǹ����������״̬ȷ����';
comment on column FRSS_APPROVAL_INFO.OPINION
  is '������';
comment on column FRSS_APPROVAL_INFO.CHECKTIME
  is '���ʱ��';
comment on column FRSS_APPROVAL_INFO.TYPE
  is '��˵ı����ͣ�ֵΪ[1~8],�ֱ��ʾ����ά���ϱ����(2��)�������������(2��)�����ϱ���ʽ��顢���ϵ��ַ���Զ��֧Ԯ��ȷ�ϡ�ά����ǲ��ȷ�ϡ�ά�޷�����ȷ�ϡ��绰ȷ��';  
comment on column FRSS_APPROVAL_INFO.STATUS
  is '��˽׶Σ�ֵΪ[0��2]���ֱ��ʾ�ύ�����ͨ�������δͨ��';
comment on column FRSS_APPROVAL_INFO.KEYID
  is '��ʾ����˱�������ID';
comment on column FRSS_APPROVAL_INFO.USERID
  is '�û�ID��Ĭ��Ϊ0';
alter table FRSS_APPROVAL_INFO add constraint FRSS_APPROVAL_INFO_PK primary key (ID);

CREATE TABLE FRSS_USER_INFO (
  ID DECIMAL(20) NOT NULL,
  USERNAME VARCHAR2(32) NOT NULL ,
  MD5PASSWORD VARCHAR2(256) NOT NULL ,
  USERTYPE NUMBER(1) NOT NULL,
  FULLNAME VARCHAR2(64) NOT NULL��
  SUBTYPE NUMBER(1),
  DESCRIPTION VARCHAR2(256),
  DEPARTMENT VARCHAR2(256),
  EMAIL VARCHAR2(64),
  CREATEMAN VARCHAR2(32)
)
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6
    minextents 1
    maxextents unlimited
  );
comment on table FRSS_USER_INFO
  is '�û���Ϣ����¼ϵͳ�����û�����Ϣ��';
comment on column FRSS_USER_INFO.ID
  is '�û����';
comment on column FRSS_USER_INFO.USERNAME
  is '�û�������¼ϵͳ���û���';
comment on column FRSS_USER_INFO.MD5PASSWORD
  is '�û�����¼�����MD5ֵ';
comment on column FRSS_USER_INFO.USERTYPE
  is '�û������ͣ���Ҫ��������������Ա�������û������ž��û����ż��û���ά�����Ĺ��Ϸַ���Ա��ר�ҡ���ҵ���ź�ά�����Ľ���Ա/���ݸ�ʽ�����Ա,����ֱ�Ϊ0��7����������Ա��ò���ϵͳĬ��ע���¼������Ҫ��ϵͳ���ṩ���ý��档�������û�ֻ�ж��Լ��������û����й���';
comment on column FRSS_USER_INFO.FULLNAME
  is '����Ӣ���������û���ʵ���ƣ����磺 ������ͬʱҲ��FRSS_APPROVAL_INFO������(CHECKER)';
comment on column FRSS_USER_INFO.SUBTYPE
  is '�û����¼��û����ͣ�6��0֮�䣬Ĭ��Ϊ0(��ʾû���¼��û�)';
comment on column FRSS_USER_INFO.DESCRIPTION
  is '�û��ļ�Ҫ����';
comment on column FRSS_USER_INFO.DEPARTMENT
  is '�û���������';
comment on column FRSS_USER_INFO.EMAIL
  is '�û���EMAIL�����ַ';
comment on column FRSS_USER_INFO.CREATEMAN
  is '�û��Ĵ�����';
alter table FRSS_USER_INFO add constraint FRSS_USER_INFO_PK primary key (ID); 

CREATE TABLE FRSS_EXPERT_INFO (
  ID DECIMAL(20) NOT NULL,
  NAME VARCHAR2(32) NOT NULL,
  DEPARTMENT VARCHAR2(256),
  EMAIL VARCHAR2(64),
  CONTACT VARCHAR2(32),
  CONTACTWAY VARCHAR2(256),
  USERID DECIMAL(20)
)
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6
    minextents 1
    maxextents unlimited
  ); 
comment on table FRSS_EXPERT_INFO
  is 'ר����Ϣ����¼Զ��Э���ĸ�λר�������Ϣ��';
comment on column FRSS_EXPERT_INFO.ID
  is 'ר�ҵı��';
comment on column FRSS_EXPERT_INFO.NAME
  is 'ר������';
comment on column FRSS_EXPERT_INFO.DEPARTMENT
  is 'ר��������λ';  
comment on column FRSS_EXPERT_INFO.EMAIL
  is 'ר�ҵ�EMAIL�����ַ';
comment on column FRSS_EXPERT_INFO.CONTACT
  is 'ר�ҵ���ϵ��';
comment on column FRSS_EXPERT_INFO.CONTACTWAY
  is 'ר����ϵ�˵���ϵ��ʽ';
alter table FRSS_EXPERT_INFO add constraint FRSS_EXPERT_INFO_PK primary key (ID);  

CREATE TABLE PROFESSIONS (
  ID DECIMAL(20) NOT NULL,
  PROFESSION VARCHAR2(32)
)
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6
    minextents 1
    maxextents unlimited
  ); 
comment on table PROFESSIONS
  is 'ר���ó���רҵ��Ϣ����¼Զ��Э���ĸ�λר���ó�רҵ��Ϣ��';
comment on column PROFESSIONS.ID
  is 'ר�ҵı��';  
comment on column PROFESSIONS.PROFESSION
  is '�ó�רҵ����';  
alter table PROFESSIONS add constraint FK_PROFESSIONS foreign key(ID) references FRSS_EXPERT_INFO(ID);   
 
CREATE TABLE FRSS_REPAIR_FEEDBACK (
  ID            NUMBER(20) not null,
  REPAIRWAY     NUMBER(1) not null,
  REPAIRDISP    VARCHAR2(256),
  RESULTS       VARCHAR2(256),
  REPAIRTIME    DATE not null,
  FAULTDISPATCH VARCHAR2(256),
  BACKNAME      VARCHAR2(256),
  BACKTYPE      VARCHAR2(256),
  BACKSOURCE    VARCHAR2(256),
  QUALITY       NUMBER(1),
  FAULTID       NUMBER(20)
)
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6
    minextents 1
    maxextents unlimited
  );
comment on table FRSS_REPAIR_FEEDBACK
  is 'װ������ά�޷�������¼װ������ά�޷����������Ϣ��';
-- Add comments to the columns 
comment on column FRSS_REPAIR_FEEDBACK.ID
  is 'װ������ά����Ϣ����';
comment on column FRSS_REPAIR_FEEDBACK.REPAIRWAY
  is 'ά�޷�ʽ��1��ʾ�ֳ�ά�ޣ�2��ʾר��Զ��֧�֣�3��ʾ�ֳ�ά�޺�Զ��֧�ֶ������ˣ�0��ʾû��ʹ���κ�ά�޷�ʽ��Ĭ��Ϊ0';
comment on column FRSS_REPAIR_FEEDBACK.REPAIRDISP
  is 'ά�����ݣ���¼ά�޵�һЩ������Ϣ��';
comment on column FRSS_REPAIR_FEEDBACK.RESULTS
  is 'ά�޽��';
comment on column FRSS_REPAIR_FEEDBACK.REPAIRTIME
  is 'ά��ʱ��';
comment on column FRSS_REPAIR_FEEDBACK.FAULTDISPATCH
  is '�������ޣ�δ��';
comment on column FRSS_REPAIR_FEEDBACK.BACKNAME
  is 'ά��ʹ�ñ�������';
comment on column FRSS_REPAIR_FEEDBACK.BACKTYPE
  is 'ά��ʹ�ñ�������';
comment on column FRSS_REPAIR_FEEDBACK.BACKSOURCE
  is '������Դ��δ��';
comment on column FRSS_REPAIR_FEEDBACK.QUALITY
  is 'ά��������Ŀǰ����1��5�����ֱ��ʾ�ǳ����⡢���⡢һ�㡢�����⡢�ǳ������⣬Ĭ��Ϊ0';
comment on column FRSS_REPAIR_FEEDBACK.FAULTID
  is '���ϱ�ţ�Ĭ��Ϊ0'; 
alter table FRSS_REPAIR_FEEDBACK add constraint FRSS_REPAIR_FEEDBACK_PK primary key (ID);  

CREATE TABLE FRSS_REMOTE_SUPPORT (
  ID DECIMAL(20) NOT NULL,
  SUPPORTYPE VARCHAR2(256),
  CHANNEL VARCHAR2(256),
  DEPARTMENT VARCHAR2(256),
  FAULTID DECIMAL(20),
  EXPERTID DECIMAL(20),
  FEEDBACKID DECIMAL(20)
)
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6
    minextents 1
    maxextents unlimited
  );
comment on table FRSS_REMOTE_SUPPORT
  is 'װ��ά��Զ��֧Ԯ��Ϣ����¼Զ��֧Ԯ�������Ϣ��';
comment on column FRSS_REMOTE_SUPPORT.ID
  is 'װ��ά��Զ��֧Ԯ��Ϣ���ţ�����';
comment on column FRSS_REMOTE_SUPPORT.SUPPORTYPE
  is 'δ��';
comment on column FRSS_REMOTE_SUPPORT.CHANNEL
  is 'Զ��֧Ԯͨ��';
comment on column FRSS_REMOTE_SUPPORT.DEPARTMENT
  is '��Զ��֧Ԯ�ĵ�λ';
comment on column FRSS_REMOTE_SUPPORT.FAULTID
  is '���ϱ��';
comment on column FRSS_REMOTE_SUPPORT.EXPERTID
  is 'ר�ұ�ţ�Ĭ��Ϊ0';
comment on column FRSS_REMOTE_SUPPORT.FEEDBACKID
  is '��������ţ�Ĭ��Ϊ0';  
alter table FRSS_REMOTE_SUPPORT add constraint FRSS_REMOTE_SUPPORT_PK primary key (ID);  

CREATE TABLE FRSS_REPAIR_DISPATCH (
  ID DECIMAL(20) NOT NULL,
  FAULTID DECIMAL(20)
)
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6
    minextents 1
    maxextents unlimited
  );
comment on table FRSS_REPAIR_DISPATCH
  is 'װ������ά����ǲ��Ϣ����¼����ά����ǲ�������Ϣ��';
comment on column FRSS_REPAIR_DISPATCH.ID
  is 'װ������ά����ǲ���ţ�����';
comment on column FRSS_REPAIR_DISPATCH.FAULTID
  is '���ϱ�ţ�Ĭ��Ϊ0';
alter table FRSS_REPAIR_DISPATCH add constraint FRSS_REPAIR_DISPATCH_PK primary key (ID);  
  
CREATE TABLE FRSS_AUDIO_INFO (
  ID DECIMAL NOT NULL,
  FILEPATH VARCHAR2(256) NOT NULL,
  TYPE NUMBER(1) NOT NULL,
  KEYID DECIMAL(20) NOT NULL,
  STATUS NUMBER(1),
  REPORTER VARCHAR2(256) NOT NULL,
  REPORTTIME DATE
) 
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 6
    minextents 1
    maxextents unlimited
  );
comment on table FRSS_AUDIO_INFO
  is '����ͨ����¼��Ϣ����¼ϵͳ�����е�ͨ����¼��Ϣ��';
comment on column FRSS_AUDIO_INFO.ID 
  is '������¼��ţ��õ�ǰʱ���ַ����������';
comment on column FRSS_AUDIO_INFO.FILEPATH
  is '��������·��';
comment on column FRSS_AUDIO_INFO.TYPE
  is '�������0��ʾ���������ϱ���1��ʾר��Զ��Э��';
comment on column FRSS_AUDIO_INFO.KEYID
  is 'FRSS_APPROVAL_INFO����FRSS_EXPERT_INFO�е�ID��ţ�Ĭ��Ϊ0';
comment on column FRSS_AUDIO_INFO.STATUS
  is '���������ϱ�״̬��0��ʾ�ϱ�״̬��1��ʾ�Ѿ�����״̬';
comment on column FRSS_AUDIO_INFO.REPORTER
  is '�����ϱ�����������ר������';
comment on column FRSS_AUDIO_INFO.REPORTTIME
  is '�����ϱ�ʱ��';
alter table FRSS_AUDIO_INFO add constraint FRSS_AUDIO_INFO_PK primary key (ID);  

create table FRSS_CLIENT_REVIEW
(
  FAULTID     NUMBER(20) not null,
  CLIENT      VARCHAR2(32) not null,
  QUALITY     NUMBER(1),
  REVIEWWAY   NUMBER(1),
  CONTACT     VARCHAR2(256),
  DISCRIPTION VARCHAR2(256)
)
tablespace FRSS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table FRSS_CLIENT_REVIEW
  is '�ͻ��طñ���¼�绰ȷ���еĻط���Ϣ';
-- Add comments to the columns 
comment on column FRSS_CLIENT_REVIEW.FAULTID
  is '��Ӧ�Ĺ��ϵ���ţ�����';
comment on column FRSS_CLIENT_REVIEW.CLIENT
  is '���ط��ˣ�Ҳ���ǿͻ�';
comment on column FRSS_CLIENT_REVIEW.QUALITY
  is 'ά����������';
comment on column FRSS_CLIENT_REVIEW.REVIEWWAY
  is '�ط��ֶΣ��绰�����棬�ʼ�';
comment on column FRSS_CLIENT_REVIEW.CONTACT
  is '��ϵ��ʽ����Ӧ����Ļط��ֶΣ��ֱ��ǵ绰���룬����ź��ʼĵ�ַ';
comment on column FRSS_CLIENT_REVIEW.DISCRIPTION
  is 'ά������';
-- Create/Recreate primary, unique and foreign key constraints 
alter table FRSS_CLIENT_REVIEW
  add constraint FRSS_CLIENT_REVIEW_PK primary key (FAULTID)
  using index 
  tablespace FRSS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
