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

import org.apache.felix.gogo.commands.Action;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.HandlerDeclaration;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.service.command.CommandSession;

import com.peergreen.store.controller.IGroupController;
import com.peergreen.store.controller.IPetalController;
import com.peergreen.store.db.client.ejb.entity.Petal;

@Component
@Command(name="giveAccessToGroup",
         scope="test",
         description="Command to give access to a group an access to a petal")
@HandlerDeclaration("<sh:command xmlns:sh='org.ow2.shelbie'/>")
public class AccessPetalForGroup implements Action {

    /**
     * Arguments are un-named values.
     */
    @Argument(index = 0,
            multiValued = false,
              description = "GroupName.")
    private String groupName;
    
    @Argument(index = 1,
            multiValued = false,
            description = "pid.")
    private int pid;
    
    
    
   
    @Requires
    private IGroupController groupController;
    @Requires
    private IPetalController petalController;

    public Object execute(CommandSession session) throws Exception {
        
            Petal p = petalController.getPetalById(pid);
            groupController.giveAccessToPetal(groupName, p.getVendor().getVendorName(), p.getArtifactId(), p.getVersion());
            System.out.println(" Petal "+ p.getPid() + " ---- " + p.getArtifactId() + " in version " + p.getVersion() + " provided by " + p.getVendor().getVendorName()
                    + " is now accesible for users of group " + groupName);
       
        
        return null;
    }
}
