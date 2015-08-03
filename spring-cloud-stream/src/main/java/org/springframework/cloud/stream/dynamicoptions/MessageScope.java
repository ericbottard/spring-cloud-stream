package org.springframework.cloud.stream.dynamicoptions;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * Created by ebottard on 17/07/15.
 */
public class MessageScope implements Scope {

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        System.out.println("Asking for " + name);
        return objectFactory.getObject();
    }

    @Override
    public Object remove(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }

    public ChannelInterceptor channelInterceptor() {
        return new MessageScopeChannelInterceptor();
    }

    public class MessageScopeChannelInterceptor extends ChannelInterceptorAdapter {
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            System.out.println("Receiving " + message + " on " + channel);
            return super.preSend(message, channel);
        }

        @Override
        public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
            super.afterSendCompletion(message, channel, sent, ex);
        }
    }
}
