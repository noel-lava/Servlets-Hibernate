<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.jlava.model.Person"%>
<html>
    <head>
        <title>Employee Management</title>
        <link href="/employee-management/font-awesome-4.7.0/css/font-awesome.css" rel="stylesheet"/>
    </head>
    
    <style>
        table, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        
        th {
             border: 3px solid black;
        }

        a.icon-block {
            display:inline-block;
            /*width:10em;*/
            text-align:center;
            color: red;
        }
    </style>
    
    <body>
        <h1 style="text-align: center;">Employee Management</h1>
        
        
            <div style="width: 80%;margin: auto">
                <form action="home" method="Post">
                    <div style="text-align: right">
                        <i style="color: red;"><%=request.getAttribute("searchResult")%></i>
                    </div>
                    <div>
                        <select name="sortBy">
                            <option value="3">Last Name</option>
                            <option value="1">GWA</option>
                            <option value="2">Date Hired</option>
                        </select>
                        <input type="submit" name="sort" value="Sort"/>
                        <input name="searchId" style="margin-left: 54.5%;" type="number"></input><input type="submit" name="search" value="Search"/>
                    </div>
                    <br/>
                    <div style="height: 70%;">
                        <table width="100%" style="text-align: center;">
                            <tr>
                                <th>ID</th>
                                <th>Last Name</th>
                                <th>First Name</th>
                                <th>GWA</th>
                                <th>Date Hired</th>
                                <th>Delete</th>
                            </tr>
                            <%  List<Person> persons = (ArrayList<Person>)request.getAttribute("persons");
                                if(persons != null) { 
                                for(Person person : persons) {
                                %>
                                <tr>
                                    <td><%=person.getId()%></td>
                                    <td><%=person.getName().getLastName()%></td>
                                    <td><%=person.getName().getFirstName()%></td>
                                    <td><%=person.getGwa()%></td>
                                    <td><%=person.getDateHired()%></td>
                                    <td>
                                        <a class="icon-block" href="home?action=delete&id=<%=person.getId()%>">
                                            <i class="fa fa-trash-o" aria-hidden="true"></i>
                                            <!-- CONFIRM -->
                                        </a>
                                    </td>
                                </tr>
                            <%} } else { %>
                                <tr>
                                    <td colspan="6" style="color: red;">No Persons Found</td>
                                </tr>
                            <% } %>
                        </table>
                    </div>
                    <div style="text-align: center;">
                        <i style="color: red;"><%=request.getAttribute("deleteResult")%></i>
                    </div>
                </form>
                <div style="text-align: right;">
                    <button href="home">Add Employee</button>
                    <button onclick="location.href='roles'">Manage Roles</button>
                </div>
            </div>
    </body>
</html> 