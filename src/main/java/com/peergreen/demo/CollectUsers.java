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
import com.peergreen.store.db.client.ejb.entity.User;


@Component
@Command(name="collectUsers",
         scope="test",
         description="Command to retrieve all the users of the store")
@HandlerDeclaration("<sh:command xmlns:sh='org.ow2.shelbie'/>")
public class CollectUsers implements Action {
    
    @Requires
    private IStoreManagment storeManagment;
   
    public Object execute(CommandSession session) throws Exception {
        
        Collection<User> users = storeManagment.collectUsers();
        Iterator<User> it = users.iterator();
        
        int i =1;
        while (it.hasNext()) {
            User p = it.next();       
            System.out.println(i + "- " +  p.getPseudo() + '\n');
            i++;
        }
        
        return null;
        
        
    }
}