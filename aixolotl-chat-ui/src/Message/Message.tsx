import React from "react";
import "./MessageStyles.css";

interface MessageProps {
	message: string;
	from: "User" | "Aixolotl";
}

const Message: React.FunctionComponent<MessageProps> = ({ message, from }) => {
	return (
		message && (
			<div
				className="message-container"
				style={{
					flexDirection: `${from === "Aixolotl" ? "row" : "row-reverse"}`,
				}}
			>
				{from === "Aixolotl" && (
					<img className="aixo-head" src="aixo.png" alt="Sfeir mascott" />
				)}
				<pre
					className="message"
					style={{
						background: `${
							from == "Aixolotl"
								? `linear-gradient(45deg, rgb(192 171 196 / 60%), rgb(37 124 136 / 76%), rgb(255 108 198 / 35%))`
								: "rgb(150 255 238 / 5%)"
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
