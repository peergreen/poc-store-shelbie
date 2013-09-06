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
@Command(name="addGroup",
scope="test",
description="Command to create a group and add it to the database")
@HandlerDeclaration("<sh:command xmlns:sh='org.ow2.shelbie'/>")
public class AddGroup implements Action {

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

        groupController.createGroup(groupName);
        System.out.println('\n'+ "The group was well created." + '\n');
        return null;


    }
}