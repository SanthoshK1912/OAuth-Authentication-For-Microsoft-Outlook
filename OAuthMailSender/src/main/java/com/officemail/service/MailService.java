package com.officemail.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.AuthorizationCodeCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.models.UserSendMailParameterSet;
import com.microsoft.graph.requests.GraphServiceClient;

@Service
public class MailService {
	
	//  ----------------------------- To Send a Mail -------------------------------------------- 
	
	public void sendMail() {
	
	    final TokenCredential credential = new AuthorizationCodeCredentialBuilder()
	            .clientId("Your-Cliet-Id")
	            .clientSecret("Client-Secret-Key")
	            .tenantId("Your-Tenant-ID")
	            .redirectUrl("redirect-url")
	            .authorizationCode("auth-code")
	            .build();
	    
	    List<String> scopes = new ArrayList<>();
	    scopes.add("https://graph.microsoft.com/Mail.Send");

	    final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(scopes, credential);

	    final GraphServiceClient graphClient =
	            GraphServiceClient
	                    .builder()
	                    .authenticationProvider(tokenCredentialAuthProvider)
	                    .buildClient();

	    Message message=new Message();
	    message.subject="Sample Email";
	    ItemBody body = new ItemBody();
	    body.content = "This is for testing purpose"; 
	    message.body = body;
	    
	    LinkedList<Recipient> toRecipientsList = new LinkedList<Recipient>();
	    Recipient toRecipients = new Recipient();
	    EmailAddress emailAddress = new EmailAddress();
	    emailAddress.address = "to-mail";
	    toRecipients.emailAddress = emailAddress;
	    toRecipientsList.add(toRecipients);
	    message.toRecipients = toRecipientsList;
	    
	    LinkedList<Recipient> ccRecipientsList = new LinkedList<Recipient>();
	    Recipient ccRecipients = new Recipient();
	    EmailAddress emailAddress1 = new EmailAddress();
	    emailAddress1.address = "cc-mail";
	    ccRecipients.emailAddress = emailAddress1;
	    ccRecipientsList.add(ccRecipients);
	    message.ccRecipients = ccRecipientsList;

	    
	    graphClient.users("from-mail")
	            	.sendMail(UserSendMailParameterSet
	                    .newBuilder()
	                    .withMessage(message)
	                    .withSaveToSentItems(true)
	                    .build())
	            .buildRequest()
	            .post();
	
		}
	
	// -------------------------- To Read a Mail ----------------------------------------
	
	public void readMail() {
	    final TokenCredential credential = new AuthorizationCodeCredentialBuilder()
	            .clientId("your-client-id")
	            .clientSecret("client-secret-key")
	            .tenantId("tenant-id")
	            .redirectUrl("redirect-url")
	            .authorizationCode("auth code")
	            .build();
	    
	    List<String> scopes = new ArrayList<>();
	    scopes.add("https://graph.microsoft.com/Mail.Read");

	    final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(scopes, credential);

	    final GraphServiceClient graphClient =
	            GraphServiceClient
	                    .builder()
	                    .authenticationProvider(tokenCredentialAuthProvider)
	                    .buildClient();


	    List<Message> messages = graphClient
	            .me()
	            .mailFolders("inbox")
	            .messages()
	            .buildRequest()
	            .get()
	            .getCurrentPage();
	    
	    int count=1;

	    for (Message message : messages) {
	    	System.out.println("------------------ Mail "+count+ "------------------------------- ");
	        System.out.println("Subject: " + message.subject);
	        System.out.println("From: " + message.sender.emailAddress.address);
	        System.out.println("Body: "+message.body.content);
	        System.out.println("----------------------------------------------------------");
	        System.out.println();
	        count++;
	    }
	}

}

