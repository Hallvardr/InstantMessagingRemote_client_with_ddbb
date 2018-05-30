/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author upcnet
 */
public class Topic implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String name;
  private Collection<Message> messageCollection;
  private Collection<Subscriber> subscriberCollection;
  private Collection<Publisher> publisherCollection;

  public Topic() {
  }

  public Topic(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Collection<Message> getMessageCollection() {
    return messageCollection;
  }

  public void setMessageCollection(Collection<Message> messageCollection) {
    this.messageCollection = messageCollection;
  }

  public Collection<Subscriber> getSubscriberCollection() {
    return subscriberCollection;
  }

  public void setSubscriberCollection(Collection<Subscriber> subscriberCollection) {
    this.subscriberCollection = subscriberCollection;
  }

  public Collection<Publisher> getPublisherCollection() {
    return publisherCollection;
  }

  public void setPublisherCollection(Collection<Publisher> publisherCollection) {
    this.publisherCollection = publisherCollection;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Topic)) {
      return false;
    }
    Topic other = (Topic) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "entity.Topic[ id=" + id + " ]";
  }
  
}
