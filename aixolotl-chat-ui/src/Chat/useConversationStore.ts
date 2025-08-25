/* eslint-disable no-case-declarations */
import { useReducer, useState } from 'react';

export type ConversationAction = 'addPromptMessage' | 'streamAixolotlMessage';

export type MessageType = {
    from: 'User' | 'Aixolotl';
    text: string;
    timestamp: number;
};

export const useConversationStore = () => {
    const [currentPrompt, setCurrentPrompt] = useState<string>();
    const [messages, dispatch] = useReducer(
        (
            messages: MessageType[],
            action: { type: ConversationAction; payload: unknown },
        ) => {
            const { type, payload } = action;
            switch (type) {
                case 'addPromptMessage':
                    setCurrentPrompt((payload as MessageType).text);
                    return [...messages, payload as MessageType];

                case 'streamAixolotlMessage':
                    const messagesLength = messages.length;
                    const lastMessage = messages.at(-1);
                    if (messagesLength && lastMessage?.from === 'User') {
                        return [
                            ...messages,
                            {
                                from: 'Aixolotl',
                                timestamp: Date.now(),
                                text: payload,
                            } as MessageType,
                        ];
                    }
                    if (lastMessage) {
                        lastMessage.text = payload as string;
                        return [...messages.slice(0, -1), lastMessage];
                    }
                    break;

                default:
                    console.log('No action found !');
                    return messages;
            }
            return [];
        },
        [],
    );

    return { currentPrompt, messages, dispatch };
};
