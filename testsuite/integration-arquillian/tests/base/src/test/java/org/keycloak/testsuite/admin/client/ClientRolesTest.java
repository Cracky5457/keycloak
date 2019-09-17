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

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.testsuite.Assert;
import org.keycloak.testsuite.admin.ApiUtil;
import org.keycloak.testsuite.util.AdminEventPaths;


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
    public void usersInCompositeRole() {
        String clientID = clientRsc.toRepresentation().getId();
        
        String role1Name = "role-1";
        String role2Name = "role-2";
        String role3Name = "role-3";
        String role4Name = "role-4-group";
        
        // group creation
        GroupRepresentation groupRep = new GroupRepresentation();
        groupRep.setName("test-role-group");
        groupRep.setPath("/test-role-group");
        try (Response response = testRealmResource().groups().add(groupRep)) {
            String groupId = ApiUtil.getCreatedId(response);
            
            getCleanup().addGroupId(groupId);

            assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.groupPath(groupId), groupRep, ResourceType.GROUP);

            // Set ID to the original rep
            groupRep.setId(groupId);
        }
        
        RoleRepresentation role1 = makeRole(role1Name);
        rolesRsc.create(role1);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId, role1Name), role1, ResourceType.CLIENT_ROLE);
        role1 = rolesRsc.get(role1Name).toRepresentation();
        
        RoleRepresentation role2 = makeRole(role2Name);
        rolesRsc.create(role2);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId, role2Name), role2, ResourceType.CLIENT_ROLE);
        role2 = rolesRsc.get(role2Name).toRepresentation();
        
        RoleRepresentation role3 = makeRole(role3Name);
        testRealmResource().roles().create(role3);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.roleResourcePath(role3Name), role3, ResourceType.REALM_ROLE);
        role3 = testRealmResource().roles().get(role3Name).toRepresentation();
        
        RoleRepresentation role4 = makeRole(role4Name);
        rolesRsc.create(role4);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourcePath(clientDbId, role4Name), role4, ResourceType.CLIENT_ROLE);
        role4 = rolesRsc.get(role4Name).toRepresentation();
        
        // We define "role-2" and "role-3" as composites of "role-1"
        List<RoleRepresentation> l = new LinkedList<>();
        l.add(role2);
        l.add(role3);
        rolesRsc.get(role1Name).addComposites(l);
        assertAdminEvents.assertEvent(getRealmId(), OperationType.CREATE, AdminEventPaths.clientRoleResourceCompositesPath(clientDbId, role1Name), l, ResourceType.CLIENT_ROLE);
        
        //create users and assign role1
        List<RoleRepresentation> roleToAdd = Collections.singletonList(rolesRsc.get(role1Name).toRepresentation());
        
        String userName = "toto";
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userName);
        testRealmResource().users().create(user);
        user = getFullUserRep(userName);
        
        testRealmResource().users().get(user.getId()).roles().clientLevel(clientID).add(roleToAdd);
        
        String userName2 = "tata";
        UserRepresentation user2 = new UserRepresentation();
        user2.setUsername(userName2);
        testRealmResource().users().create(user2);
        user2 = getFullUserRep(userName2);
        
        String userName3 = "titi";
        UserRepresentation user3 = new UserRepresentation();
        user3.setUsername(userName3);
        testRealmResource().users().create(user3);
        user3 = getFullUserRep(userName3);
        
        String userName4 = "joe";
        UserRepresentation user4 = new UserRepresentation();
        user4.setUsername(userName4);
        testRealmResource().users().create(user4);
        user4 = getFullUserRep(userName4);
        
        List<RoleRepresentation> roleToAddForUser4 = Collections.singletonList(rolesRsc.get(role4Name).toRepresentation());
        testRealmResource().users().get(user4.getId()).roles().clientLevel(clientID).add(roleToAddForUser4);
        
        /** create a group, add users and role to it **/

        // assign role1 and role4 to group
        List<RoleRepresentation> rolesForGroup = new LinkedList<RoleRepresentation>();
        rolesForGroup.add(role1);
        rolesForGroup.add(role4);
        
        testRealmResource().groups().group(groupRep.getId()).roles().clientLevel(clientDbId).add(rolesForGroup);
        
        // assign users to group
        
        testRealmResource().users().get(user2.getId()).joinGroup(groupRep.getId());
        testRealmResource().users().get(user3.getId()).joinGroup(groupRep.getId());
        
        /** units test **/
        assertTrue(rolesRsc.get(role1Name).toRepresentation().isComposite());
        
        // test if user have "role-1", he should have it because it s a direct assignation
        RoleResource roleResource1 = rolesRsc.get(role1Name);
        Set<UserRepresentation> usersInRole1 = roleResource1.getRoleUserMembers();
        assertEquals(1, usersInRole1.size());
        
        // test if user have "role-2, he should have it because "role-2" is "composite of "role-1" and the user have role 1 assigned
        RoleResource roleResource2 = rolesRsc.get(role2Name);
        Set<UserRepresentation> usersInRole2 = roleResource2.getRoleUserMembers(-1,-1,true);
        assertEquals(3, usersInRole2.size());
        
        // joe should not be in the list because he is assign to role4 which belong to the group but is absolutely not related to role-2
        assertEquals(0, usersInRole2.stream().filter(u -> u.getUsername().equals("joe")).collect(Collectors.toList()).size());
    }
}
