package de.upb.s2cx.console;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import de.upb.s2cx.database.DB2DatabaseConnector;
import de.upb.s2cx.database.IDatabaseConnector;
import de.upb.s2cx.database.OracleDatabaseConnector;
import de.upb.s2cx.database.SQLServerDatabaseConnector;
import de.upb.s2cx.event.EventGenerator;
import de.upb.s2cx.event.IEventHandler;
import de.upb.s2cx.helper.StringFunctions;
import de.upb.s2cx.parser.ParseException;
import de.upb.s2cx.parser.SQLXMLParser;
import de.upb.s2cx.parser.sql.Statement;
import de.upb.s2cx.parser.sql.TableDefinitionFactory;
import de.upb.s2cx.query.TreeBuilder;
import de.upb.s2cx.query.dataquery.IDataQuery;
import de.upb.s2cx.query.dataquery.UnionDataQuery;
import de.upb.s2cx.query.referencetree.ReferenceTreeNode;
import de.upb.s2cx.query.resultset.IDataQueryResultSet;
import de.upb.s2cx.schema.SchemaBuilder;

public class Console {

	public static void main(String[] args) {
		if (checkArgs(args)) {
			String option = args[0];
			String filename = args[1];
			try {
				String sql = StringFunctions.getStringFromFile(filename);
				Properties props = new Properties();
				props.load(new FileInputStream("conf/config.properties"));

				String engine = props.getProperty("engine");
				String eventHandlerClassName = props.getProperty("eventHandler");
				String host = props.getProperty("host");
				String port = props.getProperty("port");
				String database = props.getProperty("database");
				String username = props.getProperty("username");
				String password = props.getProperty("password");

				IDatabaseConnector dbConnector = createDatabaseConnector(engine);

				Connection connection = dbConnector.createDatabaseConnection(host, port, database, username, password);

				TableDefinitionFactory.setDatabaseConnection(connection);
				switch (option) {
				// outputs the schema
				case "schema":
					printXMLSchema(sql);
					break;
				// outputs the dataquery
				case "dataquery":
					printDataQuery(sql);
					break;
				// creates the events and passes them to the event handler
				case "s2cx":
					executeS2CX(sql, eventHandlerClassName, connection);
					break;

				}
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			printHelp();
		}
	}

	private static boolean checkArgs(String[] args) {
		if (args.length == 2) {
			String option = args[0];
			String filename = args[1];
			switch (option) {
			case "schema":
			case "dataquery":
			case "s2cx":
				File file = new File(filename);
				return file.isFile();
			}
		}
		return false;
	}

	private static void printHelp() {
		System.out.println("Please provide two arguments in the following order <option> <file>");
		System.out.println("Option can have one of the following values:");
		System.out.println(
				"schema: will print the XML schema describing the markup of the XML created by the SQL/XML query");
		System.out.println("dataquery: will print the data query created for the SQL/XML query");
		System.out.println(
				"s2cx: will create the events and passes them to the event handler defined in the properties file");
		System.out.println();
		System.out.println(
				"The second argument has to be a file containing a SQL/XML query which is supported by our parser."
						+ "Please be aware that you have to enclose the file's path with double quotes if it contains blanks.");

	}

	private static IDatabaseConnector createDatabaseConnector(String engine) throws Exception {
		switch (engine) {
		case "oracle":
			return new OracleDatabaseConnector();
		case "db2":
			return new DB2DatabaseConnector();
		case "sqlserver":
			return new SQLServerDatabaseConnector();
		}
		throw new Exception("Database engine unknown.");
	}

	private static TreeBuilder createTreeBuilder(String sql) throws ParseException, SQLException {
		SQLXMLParser parser = new SQLXMLParser(StringFunctions.stringToInputStream(sql));
		Statement statement = parser.parse();
		return new TreeBuilder(statement);
	}

	private static void executeS2CX(String sql, String eventHandlerClassName, Connection connection)
			throws SQLException, ParseException, ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		TreeBuilder tb = createTreeBuilder(sql);
		IDataQuery qb = new UnionDataQuery(tb.getQueryTreeRoot());
		IDataQueryResultSet rs = qb.execute(connection);
		ReferenceTreeNode referenceTree = tb.getReferenceTree();
		EventGenerator eg = new EventGenerator(rs, referenceTree);
		Class<?> eventHandlerClass = Class.forName(eventHandlerClassName);
		eg.setHandler((IEventHandler) eventHandlerClass.newInstance());
		eg.execute();
	}

	private static void printDataQuery(String sql) throws ParseException, SQLException {
		TreeBuilder tb = createTreeBuilder(sql);
		IDataQuery qb = new UnionDataQuery(tb.getQueryTreeRoot());
		System.out.println(qb.getSQLQuery());
	}

	private static void printXMLSchema(String sql) throws ParseException, SQLException {
		TreeBuilder tb = createTreeBuilder(sql);
		SchemaBuilder builder = new SchemaBuilder(tb.getReferenceTree());
		System.out.println(builder.getSchema());
	}
}
