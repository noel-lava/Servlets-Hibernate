<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@ page isELIgnored="false" %> 
<%@ page import="java.util.List"%>
<%@ page import="com.jlava.model.Person"%>
<%@ page import="com.jlava.model.Role"%>
<%@ page import="com.jlava.model.Contact"%>
<%@ page import="com.jlava.model.ContactType"%>
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
    	<% 	if(session.getAttribute("person") == null) response.sendRedirect("home");
    		Person person = (Person) session.getAttribute("person");
    	   	List<Role> roles = (List<Role>) request.getAttribute("roles");
    	   	List<ContactType> contactTypes = (List<ContactType>) request.getAttribute("contactTypes");
    	%>
        <h1 style="text-align: center;">Manage Employee</h1>
        <form action="manage-person" method="post">
	        <div style="width: 80%; margin: auto; text-align: center;">
	        	<table width="100%">
	        		<tr>
			        	<td style="width: 50%;">
			        		<table width="100%">
			        			<tr>
				        			<td style="text-align: right;"><label for="lastName">Last Name</label></td>
						        	<td>
						        		<input id="lastName" type="text" name="lastName" value="${person.name.lastName}" style="width: 90%;" required="required"/>
						        	</td>
					        	</tr>
					        	<tr>
						        	<td style="text-align: right;"><label for="firstName">First Name</label></td>
						        	<td><input id="firstName" type="text" name="firstName" value="${person.name.firstName}" style="width: 90%;" required="required"/></td>
					        	</tr>
					        	<tr>
						        	<td style="text-align: right;"><label for="midName">Middle Name</label></td>
						        	<td><input id="midName" type="text" name="midName" value="${person.name.midName}" style="width: 90%;" /></td>
					        	</tr>
					        	<tr>
						        	<td style="text-align: right;"><label for="suffix">Suffix</label></td>
						        	<td><input id="suffix" type="text" name="suffix" value="${person.name.suffix}" style="width: 90%;" /></td>
					        	</tr>
					        	<tr>
						        	<td style="text-align: right;"><label for="title">Title</label></td>
						        	<td><input id="title" type="text" name="title" value="${person.name.title}" style="width: 90%;" /></td>
					        	</tr>
					        	<tr>
						        	<td style="text-align: right;"><label for="birthDate">Birth Date</label></td>
						        	<td><input id="birthDate" type="date" name="birthDate" value="${person.getBirthDateToString()}" style="width: 90%;" required="required"/></td>
					        	</tr>
					        	<tr>
						        	<td style="text-align: right;"><label for="gwa">GWA</label></td>
						        	<td><input id="gwa" type="number" name="gwa" value="${person.gwa}" min="0" max="100" step="0.01" style="width: 90%;" /></td>
					        	</tr>
					        	<tr>
						        	<td style="text-align: right;"><label for="dateHired">Date Hired</label></td>
						        	<td><input id="dateHired" type="date" name="dateHired" value="${person.getDateHiredToString()}" style="width: 90%;" /></td>
					        	</tr>
					        	<tr>
						        	<td style="text-align: right;"><label for="employed">Employed</label></td>
						        	<td>
						        		<select id="employed" name="employed" style="width: 93%;">
						        			<option value="true" <%if(person.isEmployed()) {%>selected<%}%>>True</option>
						        			<option value="false" <%if(!person.isEmployed()) {%>selected<%}%>>False</option>
						        		</select>
						        	</td>
					        	</tr>
					        	<tr><td>&nbsp;</td></tr>
					        	<tr>
					        		<!-- ADDRESS -->
						        	<td style="text-align: right;"><label for="street">Street</label></td>
						        	<td><input id="street" type="text" name="street" value="${person.address.street}" style="width: 90%;" /></td>
					        	</tr>
					        	<tr>
						        	<td style="text-align: right;"><label for="barangay">Barangay</label></td>
						        	<td><input id="barangay" type="text" name="barangay" value="${person.address.barangay}" style="width: 90%;" /></td>
					        	</tr>
					        	<tr>
						        	<td style="text-align: right;"><label for="municipality">Municipality</label></td>
						        	<td><input id="municipality" type="text" name="municipality" value="${person.address.municipality}" style="width: 90%;" required="required"/></td>
					        	</tr>
					        	<tr>
						        	<td style="text-align: right;"><label for="zipCode">Zip Code</label></td>
						        	<td><input id="zipCode" type="number" name="zipCode" value="${person.address.zipCode}" style="width: 90%;" /></td>
					        	</tr>
					        	<tr><td>&nbsp;</td></tr>
			        		</table>
			        	</td>
			        	<td style="width: 50%;" valign="top">
			        		<table width="100%">
			        			<tr style="text-align: center"><td><strong>Roles</strong></td></tr>
			        			<tr>
			        				<td>
				        				<table class="solid"  width="100%">
				        					<tr class="solid">
				        						<th width="20%">Code</th>
				        						<th width="70%">Role</th>
				        						<th width="10%">Delete</th>
				        					</tr>
				        					<% if(person.getRoles() != null && person.getRoles().size() > 0) {
				        					for(Role role : person.getRoles()){ %>
				        					<tr class="solid">
				        						<td width="20%"><%=role.getCode()%></td>
				        						<td width="70%"><%=role.getRoleDesc()%></td>
				        						<td width="10%">
				        							<a onclick="document.getElementById('deleteRole<%=role.getId()%>').click();" class="icon-block" style="cursor: pointer;color:red;">
				        								<i class="fa fa-trash-o" aria-hidden="true"></i></a>
				        							<input id="deleteRole<%=role.getId()%>" type="submit" name="deleteRole" style="display: none;" value="<%=role.getId()%>" onclick="clicked(event)"/>
				        						</td>
				        					</tr>
				        					<% }} else {%>
				        					<tr class="solid">
				        						<td colspan="3"><i style="color: red;">No roles registered</i></td>
				        					</tr>
				        					<%} %>
				        				</table>
			        				</td>
			        			</tr>
			        			<tr>
	        						<td style="text-align: right;">
	        							<select name="selectRole" style="width: 80%;">
	        								<% if(roles!=null) {
	        								for(Role role : roles) {%>
	        									<option value="<%=role.getId()%>"><%=role.getRoleDesc()%></option>
	        								<% } } %>
	        							</select>
	        							<input type="submit" name="addRole" value="Add Role"/>
	        						</td>
	        					</tr>
	        					<tr>
	        						<td colspan="3">
	        							<i style="color: red;"><%=request.getAttribute("roleResult")%></i>
	        						</td>
	        					</tr>
			        			<tr style="text-align: center"><td><br/><br/><strong>Contacts</strong></td></tr>
			        			<tr>
			        				<td>
				        				<table class="solid" width="100%">
				        					<tr class="solid">
				        						<th width="20%">Type</th>
				        						<th width="60%">Contact</th>
				        						<th width="10%">Edit</th>
				        						<th width="10%">Delete</th>
				        					</tr>
				        					<% if(person.getContacts() != null && person.getContacts().size() > 0) {
				        						for(Contact contact : person.getContacts()) {%>
				        					<tr class="solid">
				        						<td>
				        							<%=contact.getContactType().getTypeDesc()%>
				        							<input type="hidden" name="contactType<%=contact.getId()%>" value="<%=contact.getContactType().getId()%>"/>
				        						</td>
				        						<td>
				        							<input type="text" name="contactDesc<%=contact.getId()%>" value="<%=contact.getContactDesc()%>"/>
				        						</td>
				        						<td>
				        							<a onclick="document.getElementById('updateContact<%=contact.getId()%>').click();" class="icon-block" style="cursor: pointer;color:green;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>

				        							<input id="updateContact<%=contact.getId()%>" type="submit" style="display: none;" name="updateContact" value="<%=contact.getId()%>" onclick="clicked(event)"/>
				        						</td>
				        						<td>
				        							<a onclick="document.getElementById('deleteContact<%=contact.getId()%>').click();" class="icon-block" style="cursor: pointer;color:red;"><i class="fa fa-trash-o" aria-hidden="true"></i></a>
	                                				
	                                				<input id="deleteContact<%=contact.getId()%>" type="submit" style="display: none;" name="deleteContact" value="<%=contact.getId()%>" onclick="clicked(event)"/>
				        						</td>
				        					</tr>
				        					<%}} else { %>
				        					<tr class="solid">
				        						<td colspan="4"><i style="color: red;">No Contacts</i></td>
				        					</tr>
				        					<% } %>
				        				</table>
			        				</td>
			        			</tr>
			        			<tr>
	        						<td style="text-align: right;">
	        							<select name="selectContactType" style="width: 20%;">
	        								<% if(contactTypes!=null) {
	        								for(ContactType type : contactTypes) {%>
	        									<option value="<%=type.getId()%>"><%=type.getTypeDesc()%></option>
	        								<% } } %>
	        							</select>
	        							<input type="text" name="contactInfo">
	        							<input type="submit" name="addContact" value="Add Contact"/>
	        						</td>
	        					</tr>
	        					<tr>
	        						<td colspan="3">
	        							<i style="color: red;"><%=request.getAttribute("contactResult")%></i>
	        						</td>
	        					</tr>
			        		</table>
			        	</td>
		        	</tr>
		        	<tr>	
		        		<td style="text-align: right; margin-right:5%;">
		        			<i style="color: red"><%=request.getAttribute("updateResult")%></i>
		        			<i style="color: green"><%=request.getAttribute("goodUpdate")%></i>
		        			<input onclick="clicked(event)" type="submit" name="updatePerson" value="Update"/>
		        		</td>
						<td style="text-align: right;">
							<input onclick="clicked(event)" type="submit" name="deletePerson" value="Delete Person"/>
							<button onclick="location.href='home';" type="button">Done</button>
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