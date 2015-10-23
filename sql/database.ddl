-- Example database from our journal paper
-- Statement are intended for Oracle databases

CREATE TABLE "EMPLOYEE" (	
  "ID" NUMBER NOT NULL ENABLE, 
  "SURNAME" VARCHAR2(20 BYTE) NOT NULL ENABLE, 
   PRIMARY KEY ("ID")
);

INSERT INTO "EMPLOYEE" VALUES (1, 'Smith');


CREATE TABLE "PROJECTEMPLOYEE" (
  "PROJECTID" NUMBER NOT NULL ENABLE, 
  "EMPLOYEEID" NUMBER NOT NULL ENABLE, 
   PRIMARY KEY ("PROJECTID", "EMPLOYEEID")
);

INSERT INTO "PROJECTEMPLOYEE" VALUES (2, 1);

CREATE TABLE "PROJECT" (
  "ID" NUMBER, 
  "NAME" VARCHAR2(20 BYTE), 
  "COMMENTS" VARCHAR2(20 BYTE), 
  PRIMARY KEY ("ID")
);

INSERT INTO "PROJECT" VALUES (1, 'World Peace', 'On Hold');
INSERT INTO "PROJECT" ("ID", "NAME") VALUES (2, 'Invisibility');