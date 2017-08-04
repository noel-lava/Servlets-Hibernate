<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@ page isELIgnored="false" %> 
<%@ page import="com.jlava.model.Person"%>
<html>
    <head>
        <title>Employee Management</title>
        <link href="/employee-management/font-awesome-4.7.0/css/font-awesome.css" rel="stylesheet"/>
    </head>

    <style>
        table .solid, tr.solid > td {
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
        <% Person newPerson = (Person)session.getAttribute("newPerson");%>
        <h1 style="text-align: center;">Add Employee</h1>
        <form action="add-person" method="post">
            <div style="width: 80%; margin: auto; text-align: center;">
                <table width="50%" style="margin: auto;">
                    <tr>
                        <td style="width: 50%;">
                            <table width="100%">
                                <tr>
                                    <td style="text-align: right;"><label for="lastName">Last Name</label></td>
                                    <td>
                                        <input id="lastName" type="text" name="lastName" value="${lastName}" style="width: 90%;" required="required"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;"><label for="firstName">First Name</label></td>
                                    <td><input id="firstName" type="text" name="firstName" value="${firstName}" style="width: 90%;" required="required"/></td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;"><label for="midName">Middle Name</label></td>
                                    <td><input id="midName" type="text" name="midName" value="${midName}" style="width: 90%;" /></td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;"><label for="suffix">Suffix</label></td>
                                    <td><input id="suffix" type="text" name="suffix" value="${suffix}" style="width: 90%;" /></td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;"><label for="title">Title</label></td>
                                    <td><input id="title" type="text" name="title" value="${title}" style="width: 90%;" /></td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;"><label for="birthDate">Birth Date</label></td>
                                    <td><input id="birthDate" type="date" name="birthDate" value="${birthDate}" style="width: 90%;" required="required"/></td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;"><label for="gwa">GWA</label></td>
                                    <td><input id="gwa" type="number" name="gwa" value="${gwa}" style="width: 90%;" /></td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;"><label for="dateHired">Date Hired</label></td>
                                    <td><input id="dateHired" type="date" name="dateHired" value="${dateHired}" style="width: 90%;" /></td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;"><label for="employed">Employed</label></td>
                                    <td>
                                        <select id="employed" name="employed" style="width: 93%;">
                                            <option value="true" <%if(newPerson.isEmployed()) {%>selected<%}%>>True</option>
                                            <option value="false" <%if(!newPerson.isEmployed()) {%>selected<%}%>>False</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr><td>&nbsp;</td></tr>
                                <tr>
                                    <!-- ADDRESS -->
                                    <td style="text-align: right;"><label for="street">Street</label></td>
                                    <td><input id="street" type="text" name="street" value="${street}" style="width: 90%;" /></td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;"><label for="barangay">Barangay</label></td>
                                    <td><input id="barangay" type="text" name="barangay" value="${barangay}" style="width: 90%;" /></td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;"><label for="municipality">Municipality</label></td>
                                    <td><input id="municipality" type="text" name="municipality" value="${municipality}" style="width: 90%;" required="required"/></td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;"><label for="zipCode">Zip Code</label></td>
                                    <td><input id="zipCode" type="number" name="zipCode" value="${zipCode}" style="width: 90%;" /></td>
                                </tr>
                                <tr><td>&nbsp;</td></tr>
                            </table>
                        </td>
                    </tr>
                    <tr>    
                        <td style="text-align: right;">
                            <i style="color: red">${updateResult}</i><br/>
                            <button onclick="location.href='home';" type="button">Cancel</button>
                            <input onclick="clicked(event)" type="submit" name="addPerson" value="Submit"/>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </body>
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js-webshim/minified/polyfiller.js"></script>
    <script>
        webshims.setOptions('forms-ext', {types: 'date'});
        webshims.polyfill('forms forms-ext');
    </script>
    <script>
        function clicked(e)
        {
            if(!confirm('Are you sure?'))e.preventDefault();
        }
    </script>
</html> 