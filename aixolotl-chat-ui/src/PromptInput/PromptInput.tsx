import * as React from 'react';
import './PromptInputStyles.css'
import type { ConversationAction } from '../Chat/useConversationStore';

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
            className='prompt-input-container'
        >
            <input
                className='input'
                ref={ref}
                type="text"
                placeholder="How can I help you ? Ask what you want !"
            ></input>
            <button
                className='stop-button'
                onClick={() => onStop && onStop()}
            >
                ⏹️
            </button>
        </div>
    );
};

export default PromptInput;
