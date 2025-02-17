import React from 'react';

interface MessageProps {
    message: string;
    from: 'User' | 'Aixolotl';
}

const Message: React.FunctionComponent<MessageProps> = ({ message, from }) => {
    return (
        message && (
            <div
                style={{
                    display: 'flex',
                    gap: '0.5rem',
                    flexDirection: `${
                        from === 'Aixolotl' ? 'row' : 'row-reverse'
                    }`,
                    width: '100%',
                    alignItems: 'flex-end',
                }}
            >
                {from === 'Aixolotl' && (
                    <img
                        style={{
                            width: '54px',
                            height: '54px',
                            borderRadius: '50%',
                        }}
                        src="aixo.png"
                        alt="Sfeir mascott"
                    />
                )}
                <pre
                    style={{
                        margin: 0,
                        whiteSpace: 'pre-wrap',
                        padding: '1rem',
                        fontSize: '0.75rem',
                        borderRadius: '1rem',
                        width: '56%',
                        color: '#efefef',
                        background: `${
                            from == 'Aixolotl'
                                ? `linear-gradient(45deg, rgb(192 171 196 / 60%), rgb(37 124 136 / 76%), rgb(255 108 198 / 35%))`
                                : 'rgb(150 255 238 / 5%)'
                        }`,
                    }}
                >
                    {message}
                </pre>
            </div>
        )
    );
};

export default Message;
