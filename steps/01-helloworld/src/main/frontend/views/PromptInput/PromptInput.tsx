import * as React from 'react';
import {
    ConversationAction,
    MessageType,
} from '../Chat/useConversationReducer';

interface PromptInputProps {
    dispatchFunction: React.Dispatch<{
        type: ConversationAction;
        payload: unknown;
    }>;
    onStop?: Function;
}

const PromptInput: React.ForwardRefRenderFunction<
    HTMLInputElement,
    PromptInputProps
> = ({ dispatchFunction, onStop }, ref) => {
    const userPromptInputRef = React.useRef<HTMLInputElement>(null);

    return (
        <div
            style={{
                position: 'fixed',
                display: 'flex',
                justifyContent: 'center',
                gap: '1rem',
                bottom: 0,
                width: '100%',
                padding: '1.25rem',
                boxSizing: 'border-box',
                background:
                    'linear-gradient(45deg, rgb(192 171 196 / 11%), rgb(37 124 136 / 17%), rgb(255 108 198 / 14%))',
                backdropFilter: 'blur(10px)',
            }}
        >
            <input
                ref={userPromptInputRef}
                type="text"
                placeholder="How can I help you ? Ask what you want !"
                style={{
                    all: 'unset',
                    borderRadius: '1.85rem',
                    width: '80%',
                    height: '3rem',
                    boxSizing: 'border-box',
                    padding: '1.75rem',
                    backdropFilter: 'blur(10px)',
                    backgroundColor: '#6868681c',
                }}
            ></input>
            <button
                type="submit"
                style={{
                    all: 'unset',
                    cursor: 'pointer',
                }}
                onClick={() => {
                    dispatchFunction({
                        type: 'addPromptMessage',
                        payload: {
                            from: 'User',
                            text: userPromptInputRef.current?.value || '',
                            timestamp: Date.now(),
                        } as MessageType,
                    });
                    if (userPromptInputRef.current) {
                        userPromptInputRef.current.value = '';
                    }
                }}
            >
                ►
            </button>
            <button
                style={{
                    all: 'unset',
                    cursor: 'pointer',
                }}
                onClick={() => onStop && onStop()}
            >
                ■
            </button>
        </div>
    );
};

export default PromptInput;
