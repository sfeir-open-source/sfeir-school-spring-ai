import * as React from 'react';
import type { ConversationAction, MessageType } from '../Chat/useConversationStore';

interface PromptInputProps {
    dispatchFunction?: React.Dispatch<{
        type: ConversationAction;
        payload: unknown;
    }>;
    onStop?: () => void;
    ref?: React.Ref<HTMLInputElement>
}

const PromptInput: React.FC<PromptInputProps> = ({ onStop, ref }) => {

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
                ref={ref}
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
