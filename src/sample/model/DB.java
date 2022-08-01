package sample.model;

import javafx.scene.image.Image;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

//t75NcQjW


public class DB {
    public String id;
    public String password;

    DB(String id, String password) {
        this.id = id;
        this.password = password;
        schemaConnect("public");
    }

    public DB() {
        this("group_m3", "t75NcQjW");
    }

    Connection con = null;
    /*private static DBModel dbmodel = null;
    public static DBModel getModel()
    {
        if (dbmodel == null)
        {
            dbmodel = new DBModel();
        }
        return dbmodel;
    }*/

    public void connect() {
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerName("134.122.66.119");
        source.setDatabaseName("group_m3");
        source.setUser(id);
        source.setPassword(password);

        try {
            con = source.getConnection();
            System.out.println("Connected to database");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void schemaConnect(String schema) {
        String sql = "set search_path to '" + schema + "'";
        Statement s1 = null;
        try {
            connect();
            s1 = con.createStatement();
            s1.execute(sql);
            System.out.println("Connected to schema " + schema);
        } catch (SQLException ex) {
        } finally {
            try {
                s1.close();
            } catch (SQLException ex) {
            }

        }

    }

    private void closeEverything() {
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void exit() {
        closeEverything();
        System.out.println("Exiting");
        System.exit(0);
    }

    public ArrayList getType() {
        ArrayList<String> arr = new ArrayList<>();
        String sql = "select job_type_name from job_type;";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql);) {
            while (rs.next()) arr.add(rs.getString(1));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return arr;
    }

    public ArrayList<String> getTypes() {
        String sql = "select job_type_name from job_type;";
        ArrayList<String> ids = new ArrayList<>();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)
        ) {
            while (rs.next()) {
                ids.add(rs.getString(1));
            }
            return ids;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public String get_password(String userName, String magor, String date) {
        String pass = "";
        String sql = "select s_password, major, date_of_birth from student where s_user_name = '\"" + userName + "\"';";


        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql);) {
            rs.next();
            String p = rs.getString(1);
            String m = rs.getString(2);
            String d = rs.getString(3);
            if (d.equals(date) && m.equals(magor)) {
                pass = encryptDecrypt(p);
                return pass;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return "Invalid";
    }

    public boolean isExistComp(String userName, String password) {
        String pass = encryptDecrypt(password);
        String sql = "select c_user_name, c_password from company where c_user_name = '\"" + userName + "\"' and c_password = '"
                + pass + "';";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql);) {
            rs.next();
            String u = rs.getString(1);
            String p = rs.getString(2);
            if (!p.equals("") && !u.equals("")) return true;
            else return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean isExistStd(String userName, String password) {
        String pass = encryptDecrypt(password);
        String sql = "select s_user_name, s_password from student where s_user_name = '\"" + userName + "\"' and s_password = '"
                + pass + "';";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql);) {
            rs.next();
            String u = rs.getString(1);
            String p = rs.getString(2);
            if (!p.equals("") && !u.equals("")) return true;
            else return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public static String encryptDecrypt(String inputString) {
        char xorKey = 'P';
        String outputString = "";
        int len = inputString.length();

        for (int i = 0; i < len; i++) {
            outputString = outputString +
                    Character.toString((char) (inputString.charAt(i) ^ xorKey));
        }

        //  System.out.println(outputString);
        return outputString;
    }

    public int getIDsJobs() {
        int count = 0;
        String sqlStatement = "select count(*) from job_listing";
        Statement statement = null;
        try {
            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

//    public String getCompanyID(String id) {
//        String sql = "select comany_id from company where c_user_name = \"?\";";
//        try (PreparedStatement st = con.prepareStatement(sql)) {
//            st.setString(1, id);
//            ResultSet rs = st.executeQuery();
//            if (rs.next()) {
//                //System.out.println(rs.getString(1));
//                return rs.getString(1);
//            }
//            return null;
//        } catch (SQLException ex) {
//
//            ex.printStackTrace();
//            return null;
//        }
//    }


    public Boolean insertJob(String company_id, String jl_id, String number_of_chance, String job_description,
                             String job_type_name, String jl_name) {
        String query = "insert into job_listing(comany_id , jl_id , number_of_chances , job_description , job_type_name , " +
                "jl_name) values (?,?,?,?,?,?);";

        try (PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, company_id);
            st.setString(2, jl_id);
            st.setString(3, number_of_chance);
            st.setString(4, job_description);
            st.setString(5, job_type_name);
            st.setString(6, jl_name);

            return st.executeUpdate() > 0;

        } catch (SQLException ex) {

            ex.printStackTrace();
            return false;
        }
    }


    public String getStudentName(String username) {
        String sql = "select name from student where s_user_name = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public String getDateOfBirth(String username) {
        String sql = "select date_of_birth from student where s_user_name = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public String getMajor(String username) {
        String sql = "select major from student where s_user_name = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public String getEmailStd(String username) {
        String sql = "select email from student where s_user_name = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public String getUsernameStd(String username) {
        String sql = "select s_user_name from student where s_user_name = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Object> getPhoneStd(String username) {
        String sql = "select phone_number from student s natural join phone_number where s_user_name = ?;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            ArrayList<Object> list = new ArrayList<>();
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(String.valueOf(rs.getInt(1)));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Project> showProjectsStd(String username) {
        ArrayList<Project> students = new ArrayList<>();

        String sql = "select project, por_desc from student natural join project where s_user_name = '" + username + "';";
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                students.add(new Project(
                        resultSet.getString(1),
                        resultSet.getString(2)
                ));
            }
            return students;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public Boolean insertProjectStd(String id, String name, String desc) {
        String query = "insert into project(id , project , por_desc) values (?,?,?);";

        try (PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, id);
            st.setString(2, name);
            st.setString(3, desc);
            return st.executeUpdate() > 0;

        } catch (SQLException ex) {

            ex.printStackTrace();
            return false;
        }
    }

    public String getIDFromUsername(String username) {
        String sql = "select id from student where s_user_name = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getCompanyName(String username) {
        String sql = "select name from company where c_user_name = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public String getCompanyUsername(String username) {
        String sql = "select c_user_name from company where c_user_name = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public String getEmailCompany(String username) {
        String sql = "select email from company where c_user_name = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public String getPhoneCompany(String username) {
        String sql = "select telephone from company where c_user_name = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public String getDescriptionCompany(String username) {
        String sql = "select c_description from company where c_user_name = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public Image getPhoto(String username) throws SQLException {

        Image img = null;
        PreparedStatement ps = con.prepareStatement("select photo from company where c_user_name = (?) ;");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs != null) {
            while (rs.next()) {
                byte[] imgBytes = rs.getBytes(1);
                img = new Image(new ByteArrayInputStream(imgBytes));
            }
            rs.close();
        }
        ps.close();
        return img;
    }

    public ArrayList<Object> getJobListingName() {
        String sql = "select jl_name from job_listing ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            ArrayList<Object> list = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public ArrayList<Object> getJobListingNameCompany(String username) {
        ArrayList<Object> list = new ArrayList<>();
        String sql = "select jl_name from job_listing natural join company where c_user_name = '" + username + "' ;";
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            return list;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Object> getJobListingDescription() {
        String sql = "select job_description from job_listing ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            ArrayList<Object> list = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Object> getJobListingDescriptionCompany(String username) {
        ArrayList<Object> list = new ArrayList<>();
        String sql = "select job_description from job_listing natural join company where c_user_name ='" + username + "' ;";
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            return list;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Object> getJobListingJobType() {
        String sql = "select job_type_name from job_listing ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            ArrayList<Object> list = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Object> getJobListingJobTypeCompany(String username) {
        ArrayList<Object> list = new ArrayList<>();
        String sql = "select job_type_name from job_listing natural join company where c_user_name = '" + username + "';";
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            return list;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }


    public ArrayList<Object> getJobListingCountOfChances() {
        String sql = "select number_of_chances from job_listing ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            ArrayList<Object> list = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Object> getJobListingCountOfChancesCompany(String username) {
        ArrayList<Object> list = new ArrayList<>();
        String sql = "select number_of_chances from job_listing natural join company where c_user_name ='" + username + "' ;";
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            return list;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }


    public ArrayList<Object> getJobListingViews() {
        String sql = "select view from job_listing ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            ArrayList<Object> list = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Object> getJobListingViewsCompany(String username) {
        ArrayList<Object> list = new ArrayList<>();
        String sql = "select view from job_listing natural join company where c_user_name = '" + username + "' ;";
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            return list;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Object> getJobListingUniqueViews() {
        String sql = "select uniqe_view from job_listing ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            ArrayList<Object> list = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Object> getJobListingUniqueViewsCompany(String username) {
        ArrayList<Object> list = new ArrayList<>();
        String sql = "select uniqe_view from job_listing natural join company where c_user_name = '" + username + "';";
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            return list;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }


    public ArrayList<Object> getJobListingApplies() {
        String sql = "select applies from job_listing ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            ArrayList<Object> list = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public ArrayList<Object> getJobListingAppliesCompany(String username) {
        ArrayList<Object> list = new ArrayList<>();
        String sql = "select applies from job_listing natural join company where c_user_name = '" + username + "' ;";
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            return list;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }
    public String getNameFromUsernameCompany(String username) {
        String sql = "select name from company where c_user_name = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        }
    }


//    public static void readJOSN_File(String path) {
//        try (PreparedStatement preparedStatement = con.prepareStatement(
//                "insert into users values(default, ?, ?, ?, ?, ?, ?, ?, ?);")) {
//            JSObject
//            JsonObject result = new JsonObject();
//            JsonParser jsonParser = new JsonParser();
//            try ( // Create an input stream for file object.dat
//                  ObjectInputStream input = new ObjectInputStream(new FileInputStream(path));
//            ) {
//                String jsonString = (String) input.readObject();
//                result = jsonParser.parse(jsonString).getAsJsonObject();
//            }
//            JsonArray jsonArrayResult = result.get("array").getAsJsonArray();
//            for (JsonElement j : jsonArrayResult) {
//                JsonObject jsonObject = j.getAsJsonObject();
//                preparedStatement.setString(1, jsonObject.get("name").getAsString());
//                preparedStatement.setString(2, jsonObject.get("country").getAsString());
//                preparedStatement.setString(3, jsonObject.get("city").getAsString());
//                preparedStatement.setString(4, jsonObject.get("state").getAsString());
//                preparedStatement.setString(5, jsonObject.get("street").getAsString());
//                preparedStatement.setString(6, jsonObject.get("email").getAsString());
//                preparedStatement.setString(7, jsonObject.get("mobile").getAsString());
//                preparedStatement.setString(8, jsonObject.get("gender").getAsString());
//
//                try {
//                    preparedStatement.executeUpdate();
//                } catch (SQLException exception) {
//                    System.out.println("!? Some data was not inserted due to redundancy");
//                    exception.printStackTrace();
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public static void WriteJOSN_File() {
//        try (PreparedStatement pst = con.prepareStatement("select row_to_json(users) from users;")) {
//            FileOutputStream file = new FileOutputStream("object.txt");
//            ResultSet r = pst.executeQuery();
//            JsonArray array = new JsonArray();
//
//            JsonParser jsonParser = new JsonParser();
//            while (r.next()) {
//                String jsonString = r.getString(1);
//                //System.out.println(jsonString);
//                array.add(jsonParser.parse(jsonString).getAsJsonObject());
//            }
//            try ( // Create an output stream for file object.dat
//                  DataOutputStream output = new DataOutputStream(file);
//            ) {
//
//                StringBuilder jsonFromJavaArrayList = new StringBuilder("{\"array\" : \n");
//                jsonFromJavaArrayList.append(array.toString());
//                jsonFromJavaArrayList.append("\n}");
//                output.writeChars(jsonFromJavaArrayList.toString());
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}
