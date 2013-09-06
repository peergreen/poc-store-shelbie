package com.peergreen.demo;

import java.util.Collection;
import java.util.Iterator;

import org.apache.felix.gogo.commands.Action;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.HandlerDeclaration;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.service.command.CommandSession;

import com.peergreen.store.controller.IStoreManagment;
import com.peergreen.store.db.client.ejb.entity.Petal;


@Component
@Command(name="petalsFromLocal",
         scope="test",
         description="Command to retrieve all the petals in a local repository")
@HandlerDeclaration("<sh:command xmlns:sh='org.ow2.shelbie'/>")
public class CollectLocalPetals implements Action {
    
    @Requires
    private IStoreManagment storeManagment;
   
    public Object execute(CommandSession session) throws Exception {
        
        Collection<Petal> petals = storeManagment.collectPetalsFromLocal();
        Iterator<Petal> it = petals.iterator();
        
        while (it.hasNext()) {
            Petal p = it.next();       
            System.out.println(p.getPid() + " - Petal " + p.getArtifactId() + " in version " + p.getVersion() + " provided by " + p.getVendor().getVendorName() + '\n');
        }
        
        return null;
        
        
    }
}
