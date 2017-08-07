<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %> 
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.jlava.model.Person"%>
<%@ page import="com.jlava.model.Role"%>
<!DOCTYPE html>
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
    </style>
    
    <body>
        <h1 style="text-align: center;">Roles</h1>
        <div style="width: 60%; text-align: center; margin: auto;">
            <div>   
                <form action="roles" method="post">            
                    <div style="height: 80%;">
                        <table width="100%" style="text-align: center;">
                            <tr>
                                <th width="20%">Code</th>
                                <th width="60%;">Role</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                            <%  List<Role> roles = (ArrayList<Role>)request.getAttribute("roles");
                                if(roles != null) {
                                for(Role role : roles) {%>
                            <tr>
                                <td><%=role.getCode()%></td>
                                <td><input style="width: 90%;" type="text" name="roleDesc-<%=role.getId()%>" value="<%=role.getRoleDesc()%>"/></td>
                                <td><a onclick="document.getElementById('update<%=role.getId()%>').click();" class="icon-block" style="cursor: pointer;color:green;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
                                <input id="update<%=role.getId()%>" type="submit" style="display: none;" name="updateRole" value="<%=role.getId()%>" onclick="clicked(event)"/>
</td>
                                <td><a onclick="document.getElementById('delete<%=role.getId()%>').click();" class="icon-block" style="cursor: pointer;color:red;"><i class="fa fa-trash-o" aria-hidden="true"></i></a>
                                <input id="delete<%=role.getId()%>" type="submit" style="display: none;" name="deleteRole" value="<%=role.getId()%>" onclick="clicked(event)"/>
</td>
                            </tr>
                            <%}} else {%>
                                <tr>
                                    <td colspan="4" style="color: red;">No Roles Found</td>
                                </tr>
                            <%}%>
                        </table>
                        <br/>
                        <div style="text-align: left; width: 100%; margin: auto;">
                            <label for="newCode">Code  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: </label>
                            <input style="width: 15%;" type="text" id="newCode" name="newCode"/><br/>
                            <label for="newRole">Description : </label>
                            <input style="width:25%;" type="text" name="newRole" id="newRole"/><br/>
                            <input type="submit" name="addNewRole" value="Add New Role" onclick="clicked(event)"/>
                        </div>
                    </div>
                </form>
            </div>
            <br/><br/>
            <div>   
                <form action="roles" method="post">            
                    <div style="height: 80%;">
                        <div style="text-align: left;">
                            <select name="filterBy">
                                <%  if(roles != null){
                                    for(Role role : roles) {%>
                                    <option value="<%=role.getId()%>" <%if(session.getAttribute("sortRoles") == role.getId()){%> selected <%}%>><%=role.getRoleDesc()%></option>
                                <%}}%>
                            </select>
                            <input type="submit" name="filter" value="Filter"/>
                        </div>
                        <table width="100%" style="text-align: center;">
                            <tr>
                                <th width="20%">ID</th>
                                <th width="80%;">Name</th>
                            </tr>
                            <%  List<Person> persons = (List<Person>)request.getAttribute("persons");
                                if(persons != null && persons.size() > 0) {
                                    for(Person person : persons) {
                            %>
                            <tr>
                                <td><%=person.getId()%></td>
                                <td><%=person.getName().getLastName()%>, <%=person.getName().getFirstName()%></td>
                            </tr>
                            <%} } else { %>
                            <tr>
                                <td colspan="2" style="color: red;">No persons found with this role</td>
                            </tr>
                            <%}%>
                        </table>
                    </div>
                </form>
            </div>
            <br/>

            <div style="text-align: center;">
                <i style="color: red;">${operationResult}</i>
                <i style="color: green;">${goodResult}</i>
            </div>

            <br/><br/>
            <div style="text-align: right;">
                <button onclick="location.href='home'" type="button">Done</button>
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