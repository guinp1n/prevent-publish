/*
 * Copyright 2018-present HiveMQ GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hivemq.extensions.helloworld;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishInboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundInput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundOutput;
import com.hivemq.extension.sdk.api.packets.publish.AckReasonCode;
import com.hivemq.extension.sdk.api.packets.publish.ModifiablePublishPacket;

/**
 * This is a very simple {@link PublishInboundInterceptor},
 * it changes the payload of every incoming PUBLISH with the topic 'hello/world' to 'Hello World!'.
 *
 * @author Yannick Weber
 * @since 4.3.1
 */
public class PreventPublishInterceptor implements PublishInboundInterceptor {

    @Override
    public void onInboundPublish(
            final @NotNull PublishInboundInput publishInboundInput,
            final @NotNull PublishInboundOutput publishInboundOutput) {

        final ModifiablePublishPacket publishPacket = publishInboundOutput.getPublishPacket();
        final String clientId = publishInboundInput.getClientInformation().getClientId();
        final String topic = publishInboundInput.getPublishPacket().getTopic();

        if (clientId.contains("prevent") || topic.contains("prevent")) {
            // prevent publish delivery on the output object
            //publishInboundOutput.preventPublishDelivery();
            //publishInboundOutput.preventPublishDelivery(AckReasonCode.TOPIC_NAME_INVALID);
            publishInboundOutput.preventPublishDelivery(AckReasonCode.TOPIC_NAME_INVALID, "It is not allowed to publish to topic: " + topic);
        }
    }
}