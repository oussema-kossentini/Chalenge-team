import { Message } from "./message.model";

export interface Chat {
    chatId?: number;
    firstUserName: string;
    secondUserName: string;
    messageList: Message[];
  }