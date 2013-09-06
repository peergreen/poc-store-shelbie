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

import com.peergreen.store.controller.IPetalController;
import com.peergreen.store.controller.IStoreManagment;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;

@Component
@Command(name="requirementsForPetal",
scope="test",
description="Command to retrieve all requirements for a specified petal.")
@HandlerDeclaration("<sh:command xmlns:sh='org.ow2.shelbie'/>")
public class CollectRequirementsForPetal implements Action {

    @Argument(index = 0,
            multiValued = false,
            description = "id.")
    private int id;

    @Requires
    private IStoreManagment storeManagment;
    @Requires
    private IPetalController petalController;

    public Object execute(CommandSession session) throws Exception {

        Petal p = petalController.getPetalById(id);

        Collection<Requirement> requirements = petalController
                .collectRequirements(
                        p.getVendor().getVendorName(),
                        p.getArtifactId(),
                        p.getVersion());

        Iterator<Requirement> it = requirements.iterator();

        while (it.hasNext()) {
            Requirement r = it.next();       
            System.out.println(
                    r.getFilter() + " in namespace: " + r.getNamespace()
                    );
        }

        return null;
    }
}
