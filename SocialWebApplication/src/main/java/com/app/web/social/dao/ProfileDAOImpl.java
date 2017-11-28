package com.app.web.social.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.social.model.PrivateMessage;
import com.app.web.social.model.Profile;

@Repository
@Transactional
public class ProfileDAOImpl implements ProfileDAO {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private FriendsDAO friendsDAO;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public Profile getProfileByNickname(String nickname) {
		Session session = this.sessionFactory.openSession();		
		Profile profile = (Profile) session.load(Profile.class, nickname);
		return profile;
	}
   
	public String getAuthenticatedUserNickname()
	{
	    return userDAO.getUserAccount( userDAO.getAuthenticatedUserUsername()).getNickname();
	}
	
	 
 
	public void editProfile (Profile profile) 
	{
		Session session = this.sessionFactory.getCurrentSession();
	    session.update(profile);
	}
	
	
	
    
	@SuppressWarnings("unchecked")
	public List<Profile> getProfilesList()
    {
    	Session session = this.sessionFactory.getCurrentSession();
		Query<Profile> query=session.createQuery("from Profile p where p.allowSearching=true");     
	    return (List<Profile>)query.list(); 
    }
    
	
    public List<Profile> searchProfiles(String sex, String city, List<String> interests)
    {   
    	boolean skipCity = city.equals(""); boolean skipSex = sex.equals(""); boolean skipInterests = interests.get(0).equals("");

    	List<Profile> allProfiles = getProfilesList();
    	
    	List<Profile> findedProfiles = new ArrayList<Profile>();
    	
    	for(int i=0;i<allProfiles.size();i++)
    	{  
    		      Profile currentProfile = allProfiles.get(i);

    			  if
    		      (    
    		    		 ( currentProfile.getCity().equals(city) || skipCity ) &&
    				     ( currentProfile.getSex().equals(sex) || skipSex ) &&
    				     ( currentProfile.getInterests().containsAll(interests) || skipInterests )
    			  )     
    		                   findedProfiles.add( currentProfile ); 
        }
    			
		return findedProfiles;   	
    }


	public List<PrivateMessage> getInbox(String nickname) {

        List<PrivateMessage> inbox = new ArrayList<PrivateMessage>();
		
		Session session = this.sessionFactory.openSession();
		
		Query<?> query=session.createQuery("from PrivateMessage");
		
		@SuppressWarnings("unchecked")
		List<PrivateMessage> messages = (List<PrivateMessage>)query.list(); 
		
		for(int i=0;i<messages.size();i++)
		{
			PrivateMessage currentMessage = messages.get(i);
			if( currentMessage.getMessageRecipients().contains(nickname) )
				 inbox.add(currentMessage);
		}
		return inbox;             
		
		/**
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<PrivateMessage> criteria = builder.createQuery(PrivateMessage.class);
		criteria. */
/** ------------------------------------------------------------------------------------------------------------------
 * 
 *  -------------------------------    DO POPRAWIENIA zapytac na stack overflow ------------------------------------
		 TUTAJ TRZEBA POPRAWI� TO ZAPYTANIE BO P.messageRecipients TO LISTA, A msg_recipient STRING */
	/** Session session = this.sessionFactory.openSession();
		String hql = "from PrivateMessage P where msgRecipiant member of P.messageRecipients";
		Query query = session.createQuery(hql);
		query.setParameter("msgRecipiant",nickname); 
		return (List<PrivateMessage>)query.list(); 
		*/

	}


	@SuppressWarnings("unchecked")
	public List<PrivateMessage> getOutbox(String nickname) {
		Session session = this.sessionFactory.openSession();
		String hql = "from PrivateMessage P where P.messageSender =:msgSender";
		Query<?> query = session.createQuery(hql).setParameter("msgSender",nickname);
		return (List<PrivateMessage>)query.list(); 
	}
	
	public PrivateMessage getMessage(Long messageId)
	{
		Session session = this.sessionFactory.openSession();		
		PrivateMessage message = (PrivateMessage) session.load(PrivateMessage.class, messageId);
		return message;
	}
	
	public void sendMessage(PrivateMessage message)
	{
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(message);	
	}	
	
	public boolean isMessageSendingAllowed(List<String> recipients)
	{
		for(String recipient : recipients)
		{
		  if(!(friendsDAO.isFriend(recipient)|| getProfileByNickname(recipient).getAllowEveryoneToSendMessage()))
			return false;
		}
		
		return true;
	}

}