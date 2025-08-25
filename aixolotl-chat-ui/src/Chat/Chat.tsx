import React from "react";
import useConversationStream from "./useConversationStream";
import PromptInput from "../PromptInput/PromptInput";
import Message from "../Message/Message";
import {
	useConversationStore,
	type MessageType,
} from "./useConversationStore";
import './ChatStyles.css'

let stopAixolotlSignal = new AbortController();

function Chat() {
	const { currentPrompt, messages, dispatch } = useConversationStore();
	const userPromptInputRef = React.useRef<HTMLInputElement>(null);
	useConversationStream(dispatch, currentPrompt, stopAixolotlSignal.signal);

    function clearInput() {
        if (userPromptInputRef.current) {
            userPromptInputRef.current.value = "";
        }
    }

	const handleSubmit = (onSubmitEvent: React.FormEvent<HTMLFormElement>) => {
		onSubmitEvent.preventDefault();
		onSubmitEvent.stopPropagation();
		dispatch({
			type: "addPromptMessage",
			payload: {
				from: "User",
				text: userPromptInputRef.current?.value || "",
				timestamp: Date.now(),
			} as MessageType,
		});
		clearInput();
	};

	return (
		<>
			<div
				className="chat-container"
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
					ref={userPromptInputRef}
					dispatchFunction={dispatch}
					onStop={() => {
						stopAixolotlSignal.abort();
						stopAixolotlSignal = new AbortController();
					}}
				></PromptInput>
			</form>
		</>
	);
}

export default Chat;
