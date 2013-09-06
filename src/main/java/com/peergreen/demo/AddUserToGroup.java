package com.peergreen.demo;

import org.apache.felix.gogo.commands.Action;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.HandlerDeclaration;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.service.command.CommandSession;

import com.peergreen.store.controller.IGroupController;

@Component
@Command(name="addUserToGroup",
scope="test",
description="Command to add to a group a new user")
@HandlerDeclaration("<sh:command xmlns:sh='org.ow2.shelbie'/>")
public class AddUserToGroup implements Action {

    /**
     * Arguments are un-named values.
     */
    @Argument(index = 0,
            multiValued = false,
            description = "groupName.")
    private String groupName;
    
    @Argument(index = 1,
            multiValued = false,
            description = "pseudo.")
    private String pseudo;

    @Requires
    private IGroupController groupController;

    public Object execute(CommandSession session) throws Exception {

        groupController.addUser(groupName, pseudo);
        System.out.println('\n' + "Sucessfully added" + '\n');
        return null;


    }
}
