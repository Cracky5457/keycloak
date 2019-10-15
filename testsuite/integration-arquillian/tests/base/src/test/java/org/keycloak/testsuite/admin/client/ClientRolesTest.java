/*
 * Copyright 2016 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.keycloak.testsuite.admin.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.testsuite.Assert;
import org.keycloak.testsuite.util.AdminEventPaths;

import java.util.Arrays;
import java.util.HashMap;

import java.util.Map;

/**
 *
 * @author Stan Silvert ssilvert@redhat.com (C) 2016 Red Hat Inc.
 */
public class ClientRolesTest extends AbstractClientTest {

    private ClientResource clientRsc;
    private String clientDbId;
    private RolesResource rolesRsc;

    @Before
    public void init() {
        clientDbId = createOidcClient("roleClient");
        clientRsc = findClientResource("roleClient");
        rolesRsc = clientRsc.roles();
    }

    @After
    public void tearDown() {
        clientRsc.remove();
    }

    private RoleRepresentation makeRole(String name) {
        RoleRepresentation role = new RoleRepresentation();
        role.setName(name);
        return role;
    }

    private boolean hasRole(RolesResource rolesRsc, String name) {
        for (RoleRepresentation role : rolesRsc.list()) {
            if (role.getName().equals(name)) return true;
        }

        return false;
    }

    @Test
    public void testAddRole() {
        RoleRepresentation role1 = makeRole("role1");
        rolesRsc.create(role1);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId, "role1"), role1, ResourceType.CLIENT_ROLE);
        assertTrue(hasRole(rolesRsc, "role1"));
    }

    @Test
    public void testRemoveRole() {
        RoleRepresentation role2 = makeRole("role2");
        rolesRsc.create(role2);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId, "role2"), role2, ResourceType.CLIENT_ROLE);

        rolesRsc.deleteRole("role2");
        assertAdminEvents.assertEvent(getRealmId(), OperationType.DELETE, AdminEventPaths.clientRoleResourcePath(clientDbId, "role2"), ResourceType.CLIENT_ROLE);

        assertFalse(hasRole(rolesRsc, "role2"));
    }

    @Test
    public void testComposites() {
        RoleRepresentation roleA = makeRole("role-a");
        rolesRsc.create(roleA);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId, "role-a"), roleA, ResourceType.CLIENT_ROLE);

        assertFalse(rolesRsc.get("role-a").toRepresentation().isComposite());
        assertEquals(0, rolesRsc.get("role-a").getRoleComposites().size());

        RoleRepresentation roleB = makeRole("role-b");
        rolesRsc.create(roleB);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId, "role-b"), roleB, ResourceType.CLIENT_ROLE);

        RoleRepresentation roleC = makeRole("role-c");
        testRealmResource().roles().create(roleC);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.roleResourcePath("role-c"), roleC, ResourceType.REALM_ROLE);

        List<RoleRepresentation> l = new LinkedList<>();
        l.add(rolesRsc.get("role-b").toRepresentation());
        l.add(testRealmResource().roles().get("role-c").toRepresentation());
        rolesRsc.get("role-a").addComposites(l);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourceCompositesPath(clientDbId, "role-a"), l, ResourceType.CLIENT_ROLE);

        Set<RoleRepresentation> composites = rolesRsc.get("role-a").getRoleComposites();

        assertTrue(rolesRsc.get("role-a").toRepresentation().isComposite());
        Assert.assertNames(composites, "role-b", "role-c");

        Set<RoleRepresentation> realmComposites = rolesRsc.get("role-a").getRealmRoleComposites();
        Assert.assertNames(realmComposites, "role-c");

        Set<RoleRepresentation> clientComposites = rolesRsc.get("role-a").getClientRoleComposites(clientRsc.toRepresentation().getId());
        Assert.assertNames(clientComposites, "role-b");

        rolesRsc.get("role-a").deleteComposites(l);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.DELETE, AdminEventPaths.clientRoleResourceCompositesPath(clientDbId, "role-a"), l, ResourceType.CLIENT_ROLE);

        assertFalse(rolesRsc.get("role-a").toRepresentation().isComposite());
        assertEquals(0, rolesRsc.get("role-a").getRoleComposites().size());
    }

    @Test
    public void usersInRole() {
        String clientID = clientRsc.toRepresentation().getId();

        // create test role on client
        String roleName = "test-role";
        RoleRepresentation role = makeRole(roleName);
        rolesRsc.create(role);
        assertTrue(hasRole(rolesRsc, roleName));
        List<RoleRepresentation> roleToAdd = Collections.singletonList(rolesRsc.get(roleName).toRepresentation());

        //create users and assign test role
        Set<UserRepresentation> users = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            String userName = "user" + i;
            UserRepresentation user = new UserRepresentation();
            user.setUsername(userName);
            testRealmResource().users().create(user);
            user = getFullUserRep(userName);
            testRealmResource().users().get(user.getId()).roles().clientLevel(clientID).add(roleToAdd);
            users.add(user);
        }

        // check if users have test role assigned
        RoleResource roleResource = rolesRsc.get(roleName);
        Set<UserRepresentation> usersInRole = roleResource.getRoleUserMembers();
        assertEquals(users.size(), usersInRole.size());
        for (UserRepresentation user : users) {
            Optional<UserRepresentation> result = usersInRole.stream().filter(u -> user.getUsername().equals(u.getUsername())).findAny();
            assertTrue(result.isPresent());
        }

        // pagination
        Set<UserRepresentation> usersInRole1 = roleResource.getRoleUserMembers(0, 5, false);
        assertEquals(5, usersInRole1.size());
        Set<UserRepresentation> usersInRole2 = roleResource.getRoleUserMembers(5, 10, false);
        assertEquals(5, usersInRole2.size());
        for (UserRepresentation user : users) {
            Optional<UserRepresentation> result1 = usersInRole1.stream().filter(u -> user.getUsername().equals(u.getUsername())).findAny();
            Optional<UserRepresentation> result2 = usersInRole2.stream().filter(u -> user.getUsername().equals(u.getUsername())).findAny();
            assertTrue((result1.isPresent() || result2.isPresent()) && !(result1.isPresent() && result2.isPresent()));
        }
    }
    
    @Test
    public void testSearchForRoles() {
        
        for(int i = 0; i<15; i++) {
            String roleName = "role"+i;
            RoleRepresentation role = makeRole(roleName);
            rolesRsc.create(role);
            assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId,roleName), role, ResourceType.CLIENT_ROLE);           
        }  
        
        String roleNameA = "abcdef";
        RoleRepresentation roleA = makeRole(roleNameA);
        rolesRsc.create(roleA);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId,roleNameA), roleA, ResourceType.CLIENT_ROLE);  
        
        String roleNameB = "defghi";
        RoleRepresentation roleB = makeRole(roleNameB);
        rolesRsc.create(roleB);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId,roleNameB), roleB, ResourceType.CLIENT_ROLE);
        
        List<RoleRepresentation> resultSearch = rolesRsc.list("def", -1, -1);
        assertEquals(2,resultSearch.size());
        
        List<RoleRepresentation> resultSearch2 = rolesRsc.list("role", -1, -1);
        assertEquals(15,resultSearch2.size());
        
        List<RoleRepresentation> resultSearchPagination = rolesRsc.list("role", 1, 5);
        assertEquals(5,resultSearchPagination.size());
    }
    
    @Test
    public void testPaginationRoles() {
        
        for(int i = 0; i<15; i++) {
            String roleName = "role"+i;
            RoleRepresentation role = makeRole(roleName);
            rolesRsc.create(role);
            assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId,roleName), role, ResourceType.CLIENT_ROLE);           
        }  
        
        List<RoleRepresentation> resultSearchWithoutPagination = rolesRsc.list();
        assertEquals(15,resultSearchWithoutPagination.size());
        
        List<RoleRepresentation> resultSearchPagination = rolesRsc.list(1, 5);
        assertEquals(5,resultSearchPagination.size());
        
        List<RoleRepresentation> resultSearchPaginationIncoherentParams = rolesRsc.list(1, null);
        assertTrue(resultSearchPaginationIncoherentParams.size() >= 15);
    }
    
    @Test
    public void testPaginationRolesCache() {
        
        for(int i = 0; i<5; i++) {
            String roleName = "paginaterole"+i;
            RoleRepresentation role = makeRole(roleName);
            rolesRsc.create(role);
            assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId,roleName), role, ResourceType.CLIENT_ROLE);        
        }   
       
        List<RoleRepresentation> resultBeforeAddingRoleToTestCache = rolesRsc.list(1, 1000);  
        
        // after a first call which init the cache, we add a new role to see if the result change
        
        RoleRepresentation role = makeRole("anewrole");
        rolesRsc.create(role);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId,"anewrole"), role, ResourceType.CLIENT_ROLE);  
        
        List<RoleRepresentation> resultafterAddingRoleToTestCache = rolesRsc.list(1, 1000);
        
        assertEquals(resultBeforeAddingRoleToTestCache.size()+1, resultafterAddingRoleToTestCache.size());
    }
    
    @Test
    public void getRolesWithFullRepresentation() {
        for(int i = 0; i<5; i++) {
            String roleName = "attributesrole"+i;
            RoleRepresentation role = makeRole(roleName);
            
            Map<String, List<String>> attributes = new HashMap<String, List<String>>();
            attributes.put("attribute1", Arrays.asList("value1","value2"));
            role.setAttributes(attributes);
                    
            rolesRsc.create(role);
            assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId,roleName), role, ResourceType.CLIENT_ROLE);  
            
            // we have to update the role to set the attributes because
            // the add role endpoint only care about name and description
            RoleResource roleToUpdate = rolesRsc.get(roleName);
            role.setId(roleToUpdate.toRepresentation().getId());
            
            roleToUpdate.update(role);
            assertAdminEvents.assertEvent(getRealmId(), OperationType.UPDATE, AdminEventPaths.clientRoleResourcePath(clientDbId,roleName), role, ResourceType.CLIENT_ROLE);  
        }
        
        List<RoleRepresentation> roles = rolesRsc.list(true);
        assertTrue(roles.get(0).getAttributes().containsKey("attribute1"));
    }

    @Test
    public void getRolesWithBriefRepresentation() {
        for(int i = 0; i<5; i++) {
            String roleName = "attributesrole"+i;
            RoleRepresentation role = makeRole(roleName);
            
            Map<String, List<String>> attributes = new HashMap<String, List<String>>();
            attributes.put("attribute1", Arrays.asList("value1","value2"));
            role.setAttributes(attributes);
                    
            rolesRsc.create(role);
            assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId,roleName), role, ResourceType.CLIENT_ROLE);  
            
            // we have to update the role to set the attributes because
            // the add role endpoint only care about name and description
            RoleResource roleToUpdate = rolesRsc.get(roleName);
            role.setId(roleToUpdate.toRepresentation().getId());
            
            roleToUpdate.update(role);
            assertAdminEvents.assertEvent(getRealmId(), OperationType.UPDATE, AdminEventPaths.clientRoleResourcePath(clientDbId,roleName), role, ResourceType.CLIENT_ROLE);         
        }
        
        List<RoleRepresentation> roles = rolesRsc.list();
        assertNull(roles.get(0).getAttributes());
    }

    @Test
    public void testParents() {
        String roleAName = "role-parent-a";
        RoleRepresentation roleA = makeRole(roleAName);
        rolesRsc.create(roleA);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE,
                AdminEventPaths.clientRoleResourcePath(clientDbId, roleAName), roleA, ResourceType.CLIENT_ROLE);

        String roleBName = "role-parent-b";
        RoleRepresentation roleB = makeRole(roleBName);
        rolesRsc.create(roleB);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE,
                AdminEventPaths.clientRoleResourcePath(clientDbId, roleBName), roleB, ResourceType.CLIENT_ROLE);

        String roleCName = "role-parent-c";
        RoleRepresentation roleC = makeRole(roleCName);
        testRealmResource().roles().create(roleC);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.roleResourcePath(roleCName),
                roleC, ResourceType.REALM_ROLE);

        // We define A composite with B and C
        List<RoleRepresentation> l = new LinkedList<>();
        l.add(rolesRsc.get(roleBName).toRepresentation());
        l.add(testRealmResource().roles().get(roleCName).toRepresentation());
        rolesRsc.get(roleAName).addComposites(l);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE,
                AdminEventPaths.clientRoleResourceCompositesPath(clientDbId, roleAName), l, ResourceType.CLIENT_ROLE);

        // We define B composite with A and C
        List<RoleRepresentation> lb = new LinkedList<>();
        lb.add(rolesRsc.get(roleAName).toRepresentation());
        lb.add(testRealmResource().roles().get(roleCName).toRepresentation());
        rolesRsc.get(roleBName).addComposites(lb);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE,
                AdminEventPaths.clientRoleResourceCompositesPath(clientDbId, roleBName), lb, ResourceType.CLIENT_ROLE);

        // So C should have two "parents" which are A and B and C is not composit itself

        assertFalse(testRealmResource().roles().get(roleCName).toRepresentation().isComposite());

        Set<RoleRepresentation> parentsOfC = testRealmResource().roles().get(roleCName).getParentsRoles();

        assertEquals(2, parentsOfC.size());
        Assert.assertNames(parentsOfC, roleAName, roleBName);
    }
}
