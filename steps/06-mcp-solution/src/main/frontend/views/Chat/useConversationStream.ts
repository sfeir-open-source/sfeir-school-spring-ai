import { Dispatch, useEffect, useRef } from 'react';

const decoder = new TextDecoder();

const useConversationStream = (
    dispatch: Dispatch<any>,
    prompt?: string,
    stopSignal?: AbortSignal,
) => {
    const aixolotlMessageRef = useRef<string>();

    const streamAixolotResponse = async () => {
        const httpResponse = fetch(
            `http://localhost:8089/conversation/stream?userMessage=${prompt}`,
            { signal: stopSignal },
        );

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
                        type: 'streamAixolotlMessage',
                        payload: aixolotlMessageRef.current,
                    });
                }
            }
        } catch (error) {
            console.error(error);
            reader?.releaseLock();
            reader?.cancel('Something went wront with our favorite Aixoltl 😭');
        }
    };

    useEffect(() => {
        if (prompt) {
            streamAixolotResponse();
        }

        return () => {
            aixolotlMessageRef.current = '';
        };
    }, [prompt]);
};

export default useConversationStream;
