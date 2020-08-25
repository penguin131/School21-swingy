create table T_CHARACTER_CLASS
(
    CHARACTER_CLASS_ID INT auto_increment,
    CLASS_NAME         VARCHAR(50) not null,
    constraint T_CHARACTER_CLASS_PK
        primary key (CHARACTER_CLASS_ID)
);

create unique index T_CHARACTER_CLASS_CHARACTER_CLASS_ID_UINDEX
    on T_CHARACTER_CLASS (CHARACTER_CLASS_ID);

create table T_CHARACTER
(
    CHARACTER_ID    INT auto_increment,
    NAME            VARCHAR(30)   not null,
    CHARACTER_CLASS INT           not null,
    ATTACK          INT,
    DEFENCE         INT,
    HITPOINT        INT,
    LEVEL           INT default 1 not null,
    EXP             INT default 0,
    constraint T_CHARACTER_T_CHARACTER_CLASS_CHARACTER_CLASS_ID_FK
        foreign key (CHARACTER_CLASS) references T_CHARACTER_CLASS (CHARACTER_CLASS_ID)
);

create unique index T_CHARACTER_CHARACTER_ID_UINDEX
    on T_CHARACTER (CHARACTER_ID);

create unique index T_CHARACTER_NAME_UINDEX
    on T_CHARACTER (NAME);

create table T_ARTIFACT_TYPE
(
    T_ARTIFACT_TYPE_ID INT,
    NAME               VARCHAR(50)
);

create table T_ARTIFACT
(
    ARTIFACT_ID   INT auto_increment,
    ARTIFACT_TYPE INT not null,
    POWER         INT,
    CHARACTER     INT not null,
    constraint T_ARTIFACT_PK
        primary key (ARTIFACT_ID),
    constraint T_ARTIFACT_T_ARTIFACT_TYPE_T_ARTIFACT_TYPE_ID_FK
        foreign key (ARTIFACT_TYPE) references T_ARTIFACT_TYPE (T_ARTIFACT_TYPE_ID),
    constraint T_ARTIFACT_T_CHARACTER_CHARACTER_ID_FK
        foreign key () references T_CHARACTER (CHARACTER_ID)
);

create unique index T_ARTIFACT_ARTIFACT_ID_UINDEX
    on T_ARTIFACT (ARTIFACT_ID);

