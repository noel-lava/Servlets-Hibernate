<!-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> -->
<!DOCTYPE html>
<html>
    <head>
        <title>Employee Management</title>
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
        <div style="width: 40%;">   
            <form action="roles" method="Post">            
                <div style="height: 80%;">
                    <table width="100%" style="text-align: center;">
                        <tr>
                            <th width="20%">Code</th>
                            <th width="60%;">Role</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        <tr>
                            <td>Code</td>
                            <td>Description</td>
                            <td><input type="button" value="id"/></td>
                            <td><input type="button" value="id"/></td>
                        </tr>
                    </table>
                </div>
            </form>
            <div style="text-align: right;">
                <button href="index.html">Add Role</button>
                <button onclick="location.href='home'">Done</button>
            </div>
        </div>
    </body>
</html> 