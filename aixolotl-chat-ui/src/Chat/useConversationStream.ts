import { useEffect, useRef, type Dispatch } from "react";
import type { ConversationAction } from "./useConversationStore";

const decoder = new TextDecoder();

const useConversationStream = (
	dispatch: Dispatch<{
		type: ConversationAction;
		payload: unknown;
	}>,
	prompt?: string,
	stopSignal?: AbortSignal
) => {
	const aixolotlMessageRef = useRef<string>(null);

	const streamAixolotResponse = async () => {
		const httpResponse = fetch(`/conversation/stream?userMessage=${prompt}`, {
			signal: stopSignal,
		});

		const reader = (await httpResponse).body?.getReader();
		try {
			while (true) {
				if (reader) {
					const { done, value } = await reader.read();
					if (done) break;
					const token = decoder.decode(value, {
						stream: true,
					});
					aixolotlMessageRef.current += token;
					dispatch({
						type: "streamAixolotlMessage",
						payload: aixolotlMessageRef.current,
					});
				}
			}
		} catch (error) {
			console.error(error);
			reader?.releaseLock();
			reader?.cancel("Something went wrong with our favorite Aixoltl 😭");
		}
	};

	useEffect(() => {
		if (prompt) {
			streamAixolotResponse();
		}

		return () => {
			aixolotlMessageRef.current = "";
		};
	}, [prompt]);
};

export default useConversationStream;
