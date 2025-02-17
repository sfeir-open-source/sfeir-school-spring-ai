import React, { useRef } from 'react';
import useConversationStream from './useConversationStream';
import PromptInput from '../PromptInput/PromptInput';
import Message from '../Message/Message';
import { useConversationStore } from './useConversationReducer';

let stopAixolotlSignal = new AbortController();

function Chat() {
    const { currentPrompt, messages, dispatch } = useConversationStore();
    const containerRef = useRef<HTMLDivElement>(null);
    useConversationStream(dispatch, currentPrompt, stopAixolotlSignal.signal);

    const handleSubmit = (onSubmitEvent: React.FormEvent<HTMLFormElement>) => {
        onSubmitEvent.preventDefault();
        onSubmitEvent.stopPropagation();
    };

    autoScrollToEnd();

    return (
        <>
            <div
                ref={containerRef}
                style={{
                    display: 'flex',
                    gap: '0.85rem',
                    flexDirection: 'column',
                    overflow: 'auto',
                    height: '89vh',
                    padding: '0.5rem 0.5rem 110px 0.5rem',
                }}
            >
                {messages.map((message) => (
                    <Message
                        key={message.timestamp}
                        message={message.text}
                        from={message.from}
                    ></Message>
                ))}
            </div>
            <form onSubmit={handleSubmit}>
                <PromptInput
                    dispatchFunction={dispatch}
                    onStop={() => {
                        stopAixolotlSignal.abort();
                        stopAixolotlSignal = new AbortController();
                    }}
                ></PromptInput>
            </form>
        </>
    );

    function autoScrollToEnd() {
        const content = containerRef.current;
        console.log({
            sHeight: content?.scrollHeight,
            clientHeight: content?.clientHeight,
        });
        if (content && content.scrollHeight > content.clientHeight) {
            containerRef.current?.scrollTo(0, content.scrollHeight);
        }
    }
}

export default Chat;
