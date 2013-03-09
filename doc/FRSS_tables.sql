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
  is '工业部门信息表，记录工业部门的相关信息。';
comment on column FRSS_FACTORY_INFO.ID
  is '工业部门的编号，主键，可以根据编号在装备信息表中查找已出厂装备型号';
comment on column FRSS_FACTORY_INFO.NAME
  is '工厂名称';
comment on column FRSS_FACTORY_INFO.ADDRESS
  is '工厂地址';
comment on column FRSS_FACTORY_INFO.CODE
  is '工厂代号';
comment on column FRSS_FACTORY_INFO.RANGE
  is '生产范围';
comment on column FRSS_FACTORY_INFO.GUARANTEE
  is '编配部队名称';
comment on column FRSS_FACTORY_INFO.GUARDADDRESS
  is '编配部队地址';
comment on column FRSS_FACTORY_INFO.ABILITY
  is '部队修理能力，目前暂分1～5级，从低到高，默认为1.';
comment on column FRSS_FACTORY_INFO.CONTACT
  is '部队联系人姓名';
comment on column FRSS_FACTORY_INFO.CONTACTWAY
  is '部队联系人的联系方式';
comment on column FRSS_FACTORY_INFO.MACHINIST
  is '维修人员姓名';
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
  is '装备信息表，记录各类装备的基础信息。';
comment on column FRSS_EQUIPMENT_INFO.ID
  is '装备表单编号，用作主键';
comment on column FRSS_EQUIPMENT_INFO.EQUIPTYPE
  is '装备型号';
comment on column FRSS_EQUIPMENT_INFO.EQUIPNUMBER
  is '装备编号';
comment on column FRSS_EQUIPMENT_INFO.EQUIPNAME
  is '装备名称';
comment on column FRSS_EQUIPMENT_INFO.CREATETIME
  is '装备生产时间';
comment on column FRSS_EQUIPMENT_INFO.DESCRIPTION
  is '装备描述信息';
comment on column FRSS_EQUIPMENT_INFO.DEPARTMENT
  is '装备使用单位';
comment on column FRSS_EQUIPMENT_INFO.OPERATER
  is '装备操作者';
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
  is '装备制造信息表，记录所有装备与制造单位的信息。';
comment on column FRSS_EQUIP_CREATING.ID
  is '装备制造信息表序列号';
comment on column FRSS_EQUIP_CREATING.EQUIPTYPE
  is '装备类型';
comment on column FRSS_EQUIP_CREATING.EQUIPNAME
  is '装备名称';
comment on column FRSS_EQUIP_CREATING.FACTORYID
  is '装备生产的工业部门序列号';
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
  is '装备故障信息表，记录所有装备的各类故障信息。';
comment on column FRSS_FAULT_INFO.ID
  is '故障编号，也可以当作是故障维修申请单号';
comment on column FRSS_FAULT_INFO.AMOUNT
  is '故障装备数量';
comment on column FRSS_FAULT_INFO.FAULTDESP
  is '装备故障描述信息';
comment on column FRSS_FAULT_INFO.FAULTTIME
  is '故障发生时间';
comment on column FRSS_FAULT_INFO.PREPROCESS
  is '装备故障前期处理情况';
comment on column FRSS_FAULT_INFO.FREQUENCY
  is '装备故障发生频次';
comment on column FRSS_FAULT_INFO.FAULTPLACE
  is '故障发生部位';
comment on column FRSS_FAULT_INFO.REPORTER
  is '故障记录者';
comment on column FRSS_FAULT_INFO.CONTACTWAY  
  is '故障记录者的联系方式';
comment on column FRSS_FAULT_INFO.REPORTTIME
  is '报修时间';
comment on column FRSS_FAULT_INFO.PHOTOS
  is '取证照片';
comment on column FRSS_FAULT_INFO.CAUSE
  is '故障原因';
comment on column FRSS_FAULT_INFO.EQUIPID
  is '装备编号，默认为0';  
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
  is '装备备件申请表，记录需申请装备备件的相关信息。';
comment on column FRSS_BACKUP_APP.ID
  is '装备备件申请单编号';
comment on column FRSS_BACKUP_APP.EQUIPLACE
  is '备件使用装备部位';
comment on column FRSS_BACKUP_APP.BACKCOUNT
  is '备件数量';
comment on column FRSS_BACKUP_APP.REPORTER
  is '备件申请记录人';
comment on column FRSS_BACKUP_APP.CONTACTWAY
  is '备件申请记录人的联系方式';
comment on column FRSS_BACKUP_APP.REPORTTIME
  is '备件申请时间';  
comment on column FRSS_BACKUP_APP.EQUIPID
  is '装备编号，默认为0';
comment on column FRSS_BACKUP_APP.FAULTID
  is '故障编号，默认为0';  
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
  is '记录审批者相关信息以及相关审批意见等';
comment on column FRSS_APPROVAL_INFO.ID
  is '审核者编号，主键';
comment on column FRSS_APPROVAL_INFO.CHECKER
  is '审核者姓名，审核者也是各种申请表单的状态确认者';
comment on column FRSS_APPROVAL_INFO.OPINION
  is '审核意见';
comment on column FRSS_APPROVAL_INFO.CHECKTIME
  is '审核时间';
comment on column FRSS_APPROVAL_INFO.TYPE
  is '审核的表单类型，值为[1~8],分别表示故障维修上报审核(2级)、备件申请审核(2级)、故障表单格式检查、故障单分发、远程支援表单确认、维修派遣表单确认、维修反馈表单确认、电话确认';  
comment on column FRSS_APPROVAL_INFO.STATUS
  is '审核阶段，值为[0～2]，分别表示提交、审核通过和审核未通过';
comment on column FRSS_APPROVAL_INFO.KEYID
  is '表示被审核表单的主键ID';
comment on column FRSS_APPROVAL_INFO.USERID
  is '用户ID，默认为0';
alter table FRSS_APPROVAL_INFO add constraint FRSS_APPROVAL_INFO_PK primary key (ID);

CREATE TABLE FRSS_USER_INFO (
  ID DECIMAL(20) NOT NULL,
  USERNAME VARCHAR2(32) NOT NULL ,
  MD5PASSWORD VARCHAR2(256) NOT NULL ,
  USERTYPE NUMBER(1) NOT NULL,
  FULLNAME VARCHAR2(64) NOT NULL，
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
  is '用户信息表，记录系统所有用户的信息。';
comment on column FRSS_USER_INFO.ID
  is '用户编号';
comment on column FRSS_USER_INFO.USERNAME
  is '用户名，登录系统的用户名';
comment on column FRSS_USER_INFO.MD5PASSWORD
  is '用户名登录密码的MD5值';
comment on column FRSS_USER_INFO.USERTYPE
  is '用户的类型，主要包括：超级管理员、军区用户、集团军用户、团级用户、维修中心故障分发人员、专家、工业部门和维修中心接线员/数据格式审核人员,代码分别为0～7，超级管理员最好采用系统默认注入记录，而不要在系统中提供设置界面。而各级用户只有对自己的下属用户进行管理。';
comment on column FRSS_USER_INFO.FULLNAME
  is '由中英文描述的用户真实名称，例如： 张三，同时也是FRSS_APPROVAL_INFO表的外键(CHECKER)';
comment on column FRSS_USER_INFO.SUBTYPE
  is '用户的下级用户类型，6～0之间，默认为0(表示没有下级用户)';
comment on column FRSS_USER_INFO.DESCRIPTION
  is '用户的简要描述';
comment on column FRSS_USER_INFO.DEPARTMENT
  is '用户所属部门';
comment on column FRSS_USER_INFO.EMAIL
  is '用户的EMAIL邮箱地址';
comment on column FRSS_USER_INFO.CREATEMAN
  is '用户的创建人';
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
  is '专家信息表，记录远程协助的各位专家相关信息。';
comment on column FRSS_EXPERT_INFO.ID
  is '专家的编号';
comment on column FRSS_EXPERT_INFO.NAME
  is '专家姓名';
comment on column FRSS_EXPERT_INFO.DEPARTMENT
  is '专家所属单位';  
comment on column FRSS_EXPERT_INFO.EMAIL
  is '专家的EMAIL邮箱地址';
comment on column FRSS_EXPERT_INFO.CONTACT
  is '专家的联系人';
comment on column FRSS_EXPERT_INFO.CONTACTWAY
  is '专家联系人的联系方式';
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
  is '专家擅长的专业信息表，记录远程协助的各位专家擅长专业信息。';
comment on column PROFESSIONS.ID
  is '专家的编号';  
comment on column PROFESSIONS.PROFESSION
  is '擅长专业名称';  
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
  is '装备故障维修反馈表，记录装备故障维修反馈的相关信息。';
-- Add comments to the columns 
comment on column FRSS_REPAIR_FEEDBACK.ID
  is '装备故障维修信息单号';
comment on column FRSS_REPAIR_FEEDBACK.REPAIRWAY
  is '维修方式，1表示现场维修，2表示专家远程支持，3表示现场维修和远程支持都采用了，0表示没有使用任何维修方式。默认为0';
comment on column FRSS_REPAIR_FEEDBACK.REPAIRDISP
  is '维修内容，记录维修的一些描述信息。';
comment on column FRSS_REPAIR_FEEDBACK.RESULTS
  is '维修结果';
comment on column FRSS_REPAIR_FEEDBACK.REPAIRTIME
  is '维修时间';
comment on column FRSS_REPAIR_FEEDBACK.FAULTDISPATCH
  is '故障派修，未定';
comment on column FRSS_REPAIR_FEEDBACK.BACKNAME
  is '维修使用备件名称';
comment on column FRSS_REPAIR_FEEDBACK.BACKTYPE
  is '维修使用备件类型';
comment on column FRSS_REPAIR_FEEDBACK.BACKSOURCE
  is '备件来源，未定';
comment on column FRSS_REPAIR_FEEDBACK.QUALITY
  is '维修质量，目前划分1～5级，分别表示非常满意、满意、一般、不满意、非常不满意，默认为0';
comment on column FRSS_REPAIR_FEEDBACK.FAULTID
  is '故障编号，默认为0'; 
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
  is '装备维修远程支援信息表，记录远程支援的相关信息。';
comment on column FRSS_REMOTE_SUPPORT.ID
  is '装备维修远程支援信息单号，主键';
comment on column FRSS_REMOTE_SUPPORT.SUPPORTYPE
  is '未定';
comment on column FRSS_REMOTE_SUPPORT.CHANNEL
  is '远程支援通道';
comment on column FRSS_REMOTE_SUPPORT.DEPARTMENT
  is '被远程支援的单位';
comment on column FRSS_REMOTE_SUPPORT.FAULTID
  is '故障编号';
comment on column FRSS_REMOTE_SUPPORT.EXPERTID
  is '专家编号，默认为0';
comment on column FRSS_REMOTE_SUPPORT.FEEDBACKID
  is '反馈单编号，默认为0';  
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
  is '装备故障维修派遣信息表，记录故障维修派遣的相关信息。';
comment on column FRSS_REPAIR_DISPATCH.ID
  is '装备故障维修派遣单号，主键';
comment on column FRSS_REPAIR_DISPATCH.FAULTID
  is '故障编号，默认为0';
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
  is '语音通话记录信息表，记录系统中所有的通话记录信息。';
comment on column FRSS_AUDIO_INFO.ID 
  is '语音记录编号，用当前时间字符串当作编号';
comment on column FRSS_AUDIO_INFO.FILEPATH
  is '语音保存路径';
comment on column FRSS_AUDIO_INFO.TYPE
  is '语音类别，0表示故障语音上报、1表示专家远程协助';
comment on column FRSS_AUDIO_INFO.KEYID
  is 'FRSS_APPROVAL_INFO或者FRSS_EXPERT_INFO中的ID编号，默认为0';
comment on column FRSS_AUDIO_INFO.STATUS
  is '离线语音上报状态，0表示上报状态，1表示已经处理状态';
comment on column FRSS_AUDIO_INFO.REPORTER
  is '语音上报人姓名或者专家姓名';
comment on column FRSS_AUDIO_INFO.REPORTTIME
  is '语音上报时间';
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
  is '客户回访表，记录电话确认中的回访信息';
-- Add comments to the columns 
comment on column FRSS_CLIENT_REVIEW.FAULTID
  is '对应的故障单编号，主键';
comment on column FRSS_CLIENT_REVIEW.CLIENT
  is '被回访人，也就是客户';
comment on column FRSS_CLIENT_REVIEW.QUALITY
  is '维修质量反馈';
comment on column FRSS_CLIENT_REVIEW.REVIEWWAY
  is '回访手段，电话，传真，邮件';
comment on column FRSS_CLIENT_REVIEW.CONTACT
  is '联系方式，对应上面的回访手段，分别是电话号码，传真号和邮寄地址';
comment on column FRSS_CLIENT_REVIEW.DISCRIPTION
  is '维修描述';
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
