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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.felix.gogo.commands.Action;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.HandlerDeclaration;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.service.command.CommandSession;

import com.peergreen.store.controller.IPetalController;
import com.peergreen.store.controller.util.DependencyRequest;
import com.peergreen.store.controller.util.DependencyResult;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;

@Component
@Command(name="petalDependencies",
scope="test",
description="Command to get petal's dependencies.")
@HandlerDeclaration("<sh:command xmlns:sh='org.ow2.shelbie'/>")
public class GetTransitiveDependencies implements Action {

    @Argument(index = 0,
            multiValued = false,
            description = "Petal id")
    private int id;

    @Requires
    private IPetalController petalController;

    public Object execute(CommandSession session) throws Exception {
        Petal p = petalController.getPetalById(id);

        DependencyRequest req = new DependencyRequest();
        req.setVendor(p.getVendor());
        req.setArtifactId(p.getArtifactId());
        req.setVersion(p.getVersion());

        DependencyResult res = petalController
                .getTransitiveDependencies(req);

        System.out.println("Dependencies for petal " + p.getPid() + " - "
                + p.getArtifactId() + "-" + p.getVersion() + " provided by "
                + p.getVendor().getVendorName() + ":");

        if (res.getResolvedRequirements().size() > 0) {
            System.out.println("   Resolved dependencies:");
            Map<Requirement, Set<Petal>> resolved = res.getResolvedRequirements();
            for (Entry<Requirement, Set<Petal>> entry : resolved.entrySet()) {
                System.out.println("      * " + entry.getKey().getFilter()
                        + " namespace: " + entry.getKey().getNamespace());
                for (Petal petal : entry.getValue()) {
                    System.out.println("         " + petal.getPid() + " - "
                            + petal.getArtifactId() + "-"
                            + petal.getVersion() + " provided by "
                            + petal.getVendor().getVendorName());
                }
            }
        }
        
        if (res.getUnresolvedRequirements().size() > 0) {
            System.out.println("   Unresolved dependencies:");
            for (Requirement r : res.getUnresolvedRequirements()) {
                System.out.println("      * " + r.getFilter()
                        + " in namespace: " + r.getNamespace());
            }
        }

        return null;
    }
}
