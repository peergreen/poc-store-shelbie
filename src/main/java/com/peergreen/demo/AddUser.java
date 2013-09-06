package com.peergreen.demo;

import org.apache.felix.gogo.commands.Action;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.HandlerDeclaration;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.service.command.CommandSession;

import com.peergreen.store.controller.IUserController;

@Component
@Command(name="addUser",
         scope="test",
         description="Command to create an user and add it in the database")
@HandlerDeclaration("<sh:command xmlns:sh='org.ow2.shelbie'/>")
public class AddUser implements Action {

    /**
     * Arguments are un-named values.
     */
    @Argument(index = 0,
            multiValued = false,
              description = "pseudo.")
    private String pseudo;
    
    @Argument(index = 1,
            multiValued = false,
            description = "password.")
    private String password;
    
    @Argument(index = 2,
            multiValued = false,
            description = "email.")
    private String email;
    

   
    @Requires
    private IUserController userController;

    public Object execute(CommandSession session) throws Exception {

        userController.addUser(pseudo, password, email);
        System.out.println('\n' + "The user was well created." + '\n');
        
        return null;
    }
}