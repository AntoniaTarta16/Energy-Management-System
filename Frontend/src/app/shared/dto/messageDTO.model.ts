export interface MessageDTO {
  senderId: string;
  message: string;
  messageId?: string;
  read?: boolean;
}
