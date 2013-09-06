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

import org.apache.felix.gogo.commands.Action;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.HandlerDeclaration;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.service.command.CommandSession;

import com.peergreen.store.controller.IGroupController;
import com.peergreen.store.db.client.ejb.entity.Petal;

@Component
@Command(name="groupPetals",
scope="test",
description="Command to get petals accesible for a specified group")
@HandlerDeclaration("<sh:command xmlns:sh='org.ow2.shelbie'/>")
public class CollectPetalsByGroup implements Action {

    /**
     * Arguments are un-named values.
     */
    @Argument(index = 0,
            multiValued = false,
            description = "GroupName.")
    private String groupName;

    @Requires
    private IGroupController groupController;

    public Object execute(CommandSession session) throws Exception {

        Collection<Petal> result = groupController.collectPetals(groupName);
        System.out.println(" Group " + groupName + " have access to " + result.size() + " petals : " + '\n');
        Iterator<Petal> it = result.iterator();
        while(it.hasNext()){
            Petal p = it.next();
           
            System.out.println('\t' + p.getPid() + " - Petal " + p.getArtifactId() + " in version " + p.getVersion() + " provided by " + p.getVendor().getVendorName() + '\n');

        }

        return null;
    }
}
