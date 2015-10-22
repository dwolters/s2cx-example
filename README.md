# S2CX - Example
In this repository, you find an example showing how to use the [S2CX library]( https://github.com/dwolters/s2cx). For more information on S2CX, have a look at the [S2CX repository]( https://github.com/dwolters/s2cx). The example contains a small console-based application which shows how to do the following:
-	Creation of a data query for a given SQL/XML query.
-	Creation of a schema describing the markup of the XML data retrieved by a SQL/XML query
-	Event creation based on a given SQL/XML query.

The example contains two event handlers. One simply outputs the events that are created. The other one prints the retrieved XML. Please note that the latter one prints attributes as XML tags with the prefix @. S2CX uses this notation for attributes as a simplification for XML processing. 

In order to use the example program you have to setup a database. The SQL statements to create the example database from our journal paper can be found in the [sql](sql/) subdirectory. An [example query](sql/example.sql) is provided as well. Please have a look at the limitations and assumptions section in the README file of the S2CX repository before you write your own queries. The [statements to create the example database](sql/database.ddl) are intended for an Oracle database. If you want to use another database, you will probably have to adapt these statements. The S2CX has successfully been used with Oracle XE, SQL Server, and DB2. The approach itself relies solely on SQL, but due to the differences in the SQL support, the implementation does not work with any SQL-based database engine. There is a high probability that the S2CX library has to be adapted if another database engine should be used. 

To run the console program, the [config.properties](conf/config.properties) file in the subdirectory conf has to be edited. In the properties file, you can define the database engine (oracle, db2, or sqlserver), the host and port as well as credentials for the database. Furthermore, you can define which event handler should be used ([de.upb.s2cx.console.XMLEventHandler](src/de/upb/s2cx/XMLEventHandler.java) or [de.upb.s2cx.console.EventPrintHandler](src/de/upb/s2cx/EventPrintHandler.java)).

Depending on which database engine you use, you have to first add the corresponding JDBC drivers to the build path. You can find the JDBC drivers here: 

* Oracle: http://www.oracle.com/technetwork/database/enterprise-edition/jdbc-111060-084321.html
* DB2: http://www-01.ibm.com/support/docview.wss?uid=swg21363866
* SQL Server: http://jtds.sourceforge.net

The console program takes two arguments. The first one describes what should be done:
* _schema_: will print out the XML schema describing the markup of the XML created by the SQL/XML query
* _dataquery_: will print out the data query created for the SQL/XML query
* _s2cx_: will create the events and passes them to the event handler defined in the properties file

The second argument has to be a path pointing to a file containing an SQL/XML query. An example query can be found in [sql/example.sql] (sql/example.sql).

Please also have a look at the [S2CX repository](https://github.com/dwolters/s2cx) for more information on the approach as well as for a list of our publications.


