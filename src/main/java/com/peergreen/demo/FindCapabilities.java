/**
 * Copyright 2010 OW2 Shelbie
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.peergreen.demo;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.apache.felix.gogo.commands.Action;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.HandlerDeclaration;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.service.command.CommandSession;

import com.peergreen.store.controller.IPetalController;
import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.session.api.ISessionRequirement;
import com.peergreen.store.db.client.exception.EntityAlreadyExistsException;

@Component
@Command(name="findCapabilities",
         scope="test",
         description="Command to test how works the system" +
         		"to match requirements with capabilities")
@HandlerDeclaration("<sh:command xmlns:sh='org.ow2.shelbie'/>")
public class FindCapabilities implements Action {

    /**
     * Arguments are un-named values.
     */
    @Argument(index = 0,
            multiValued = false,
              description = "Namespace.")
    private String namespace;
    
    @Argument(index = 1,
            multiValued = false,
            description = "LDAP filter.")
    private String filter;
    
    @Requires
    private ISessionRequirement requirementSession;
    @Requires
    private IPetalController petalController;

    public Object execute(CommandSession session) throws Exception {

        Requirement req = null;
        try {
            req = petalController.createRequirement(UUID.randomUUID().toString(), namespace, filter);
        } catch (EntityAlreadyExistsException e) {
            e.printStackTrace();
        }

        // search for capabilities meeting this requirement
        Collection<Capability> listResolvedCapabilities = requirementSession.findCapabilities(req);

        System.out.println();
        if (listResolvedCapabilities.size() <= 0) {
            System.out.println("Search results: no result.");
        } else {
            System.out.println("Search results:");
        }

        int i = 1;
        Iterator<Capability> it = listResolvedCapabilities.iterator();
        while (it.hasNext()) {
            Capability c = it.next();
            
            System.out.println(i + " - " + c.getCapabilityId() + " - " + c.getCapabilityName());
            i++;
        }
        
        return null;
    }
}
