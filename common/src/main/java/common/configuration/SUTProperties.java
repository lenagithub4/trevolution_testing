package common.configuration;

import common.file.PathHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;




    /**
     * The SUTProperties class handles the translation of the property file of the test project to system properties.
     */
    public class SUTProperties {
        private static String host = "";
        private static String serverName = "";
        private static String port = "";
        private static String dbName = "";
        private static String dbuserpassword = "";
        private static String m2mpassword = "";
        private static String dbuser = "";
        private static String user = "";
        private static String admin = "";
        private static String m2muser = "";
        private static String chromedriverFile = "";
        private static int apiConnectionTimeoutSeconds;
        private static String headless = "";
        private static String indexation = "";
        private static String simulateSlowNetwork = "";


        private SUTProperties() {
            throw new IllegalStateException("Utility class");
        }


        /**
         * @return Defined hostname
         * @throws IOException Thrown if properties could not be read
         */
        public static String getHost() throws IOException {
            checkIfInitialized();
            return host;
        }



        /**
         * @return Defined dbuser password
         * @throws IOException Thrown if properties could not be read
         */
        public static String getDbUserPassword() throws IOException {
            checkIfInitialized();
            return dbuserpassword;
        }

        /**
         * @return Defined M2M user password
         * @throws IOException Thrown if properties could not be read
         */
        public static String getM2mPassword() throws IOException {
            checkIfInitialized();
            return m2mpassword;
        }

        /**
         * @return Defined user name
         * @throws IOException Thrown if properties could not be read
         */
        public static String getUser() throws IOException {
            checkIfInitialized();
            return user;
        }

        /**
         * @return Defined dbuser name
         * @throws IOException Thrown if properties could not be read
         */
        public static String getDbUser() throws IOException {
            checkIfInitialized();
            return dbuser;
        }

        /**
         * @return Defined M2M user name
         * @throws IOException Thrown if properties could not be read
         */
        public static String getM2mUser() throws IOException {
            checkIfInitialized();
            return m2muser;
        }


        /**
         * @return Defined admin name
         * @throws IOException Thrown if properties could not be read
         */
        public static String getAdmin() throws IOException {
            checkIfInitialized();
            return admin;
        }


        /**
         * @return Defined database server name
         * @throws IOException Thrown if properties could not be read
         */
        public static String getServerName() throws IOException {
            checkIfInitialized();
            return serverName;
        }

        /**
         * @return Defined database port
         * @throws IOException Thrown if properties could not be read
         */
        public static String getPort() throws IOException {
            checkIfInitialized();
            return port;
        }

        /**
         * @return Defined database name
         * @throws IOException Thrown if properties could not be read
         */
        public static String getDbName() throws IOException {
            checkIfInitialized();
            return dbName;
        }

        /**
         * @return Path to windows chromedriver binary (including binary name)
         * @throws IOException Thrown if properties could not be read
         */
        public static String getChromedriverPath() throws IOException {
            checkIfInitialized();
            return chromedriverFile;
        }



        /**
         * @return connection timeout for API-calls in general
         * @throws IOException Thrown if properties could not be read
         */
        public static int getAPIConnectionTimeoutSeconds() throws IOException {
            checkIfInitialized();
            return apiConnectionTimeoutSeconds;
        }

        /**
         * @return headless mode status (if true, Selenium tests will run in headless mode)
         * @throws IOException Thrown if properties could not be read
         */
        public static String getHeadlessModeStatus() throws IOException {
            checkIfInitialized();
            return headless;
        }

        /**
         * @return simulateSlowNetwork status (if true, Selenium tests will run with simulated Slow Network)
         * @throws IOException Thrown if properties could not be read
         */
        public static String getSimulateSlowNetworkStatus() throws IOException {
            checkIfInitialized();
            return simulateSlowNetwork;
        }

        /**
         * Checks whether indexation is enabled according to the loaded configuration.
         * <p>
         * This value is loaded from {@code run.properties} under the key {@code sut.indexation}.
         * If the property is not defined (i.e., {@code null}), the method returns {@code true} by default.
         *
         * @return {@code true} if indexation is enabled, or not defined; {@code false} if explicitly disabled
         * @throws IOException if the properties file could not be read during initialization
         */
        public static boolean isIndexationEnabled() throws IOException {
            checkIfInitialized();
            return indexation == null || Boolean.parseBoolean(indexation);
        }



        /**
         * Checks id properties have been initialised, and loads properties if they are not.
         *
         * @throws IOException thrown if resourceStream could not be established
         */
        public static void checkIfInitialized() throws IOException {
            if (host.isEmpty()) {
                loadProperties();
            }
        }


        /***
         * Reads the by maven created run.properties, then composes properties to be used within
         * this test automation project using the getters.
         */
        private static void loadProperties() throws IOException {

            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("run.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            // Must be injected as maven D-flag
            host = properties.getProperty("sut.host");
            serverName = properties.getProperty("sut.server");
            dbName = properties.getProperty("sut.dbname");
            port = properties.getProperty("sut.dbport");
            user = properties.getProperty("sut.user");
            admin = properties.getProperty("sut.admin");
            dbuser = properties.getProperty("sut.dbuser");
            dbuserpassword = properties.getProperty("sut.dbuserpw");
            m2muser = properties.getProperty("sut.m2muser");
            m2mpassword = properties.getProperty("sut.m2mpassword");
            headless = properties.getProperty("sut.headless");
            indexation = properties.getProperty("sut.indexation");
            simulateSlowNetwork=properties.getProperty("sut.simulateSlowNetwork");


            // Persistent properties defined in project pom
            apiConnectionTimeoutSeconds = Integer.parseInt(properties.getProperty("sut.apiconnectiontimeoutseconds"));

            //Selenium related drivers

                chromedriverFile = PathHelper.getLibFolder(new File("").getAbsolutePath()) + properties.getProperty("tas.winChromedriverFile");

        }
}
