SELECT
  XMLELEMENT(NAME "projects",
    XMLAGG(
      XMLELEMENT(NAME "project", 
        XMLATTRIBUTES("p"."NAME" AS "name"),
        (SELECT
          XMLAGG(
            XMLFOREST("e"."SURNAME" AS "employee")
            ORDER BY "e"."SURNAME"
          )
        FROM "EMPLOYEE" "e", "PROJECTEMPLOYEE" "pe"
        WHERE "pe"."EMPLOYEEID" = "e"."ID"
          AND "p"."ID" = "pe"."PROJECTID"),
        XMLFOREST("p"."COMMENTS" AS "comment")
      )
      ORDER BY "p"."NAME"
    )
  )
FROM "PROJECT" "p"