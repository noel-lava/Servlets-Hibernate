<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ page isELIgnored="false" %> 
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.jlava.model.Person"%>
<%@ page import="java.lang.Integer"%>
<html>
    <%List<Person> persons = (List<Person>)session.getAttribute("persons");%>
    <head>
        <title>Employee Management</title>
        <link href="/employee-management/font-awesome-4.7.0/css/font-awesome.css" rel="stylesheet"/>
    </head>
    
    <style>
        table.solid, tr.solid > td {
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
                    <table width="100%;">
                        <tr>
                            <td colspan="2" style="text-align: right">
                                <i style="color: red;">${searchResult}</i>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <select name="sortBy">
                                    <option value="3" <%if(Integer.parseInt(session.getAttribute("sortBy").toString()) == 3){%>selected<%}%> >
                                        Last Name
                                    </option>
                                    <option value="1"<%if(Integer.parseInt(session.getAttribute("sortBy").toString()) == 1){%>selected<%}%> >
                                        GWA
                                    </option>
                                    <option value="2"<%if(Integer.parseInt(session.getAttribute("sortBy").toString()) == 2){%>selected<%}%> >
                                        Date Hired
                                    </option>
                                </select>
                                <input type="submit" name="sort" value="Sort"/>
                            </td>
                            <td style="text-align: right;">
                                <input name="searchId" type="number" min="1"></input><input type="submit" name="search" value="Search"/>
                            </td>
                        </tr>
                    </table>
                    <br/>
                    <div style="height: 70%;">
                        <table width="100%" style="text-align: center;" class="solid">
                            <tr class="solid">
                                <th>ID</th>
                                <th>Last Name</th>
                                <th>First Name</th>
                                <th>GWA</th>
                                <th>Date Hired</th>
                                <th>Delete</th>
                            </tr>
                            <%  if(persons != null) { 
                                for(Person person : persons) {
                                %>
                                <tr class="solid">
                                    <td><a href="home?search=Search&searchId=<%=person.getId()%>" style="text-decoration: none; color:inherit;"><%=person.getId()%></a></td>
                                    <td><%=person.getName().getLastName()%></td>
                                    <td><%=person.getName().getFirstName()%></td>
                                    <td><%=person.getGwa()%></td>
                                    <td><%=person.getDateHiredToString()%></td>
                                    <td>
                                        <a onclick="document.getElementById('delete<%=person.getId()%>').click();" class="icon-block" style="cursor: pointer;color:red;" class="icon-block">
                                            <i class="fa fa-trash-o" aria-hidden="true"></i>
                                        </a>
                                        <input id="delete<%=person.getId()%>" type="submit" style="display: none;" name="delete" value="<%=person.getId()%>" onclick="clicked(event)"/>
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
                        <i style="color: red;">${deleteResult}</i>
                    </div>
                </form>
                <div style="text-align: right;">
                    <button onclick="location.href='add-person'" type="button">Add Employee</button>
                    <button onclick="location.href='roles'" type="button">Manage Roles</button>
                </div>
            </div>
    </body>

    <script>
        function clicked(e)
        {
            if(!confirm('Are you sure?'))e.preventDefault();
        }
    </script>
</html> 