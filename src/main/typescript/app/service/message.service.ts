import { Injectable, ErrorHandler } from '@angular/core';

import { Subject } from 'rxjs';

export enum MessageType {
  heartbeat = "heartbeat",
  reset = "reset",
  progress = "progress",
  info = "info",
  warning = "warning",
  error = "error",
  loading = "loading",
  notify = "notify"
}

export class Message {
  messageType?: MessageType;
  message?: string;
  requestTimeStamp?: string;
  requestIdentifier?: any;
  image?: string;
  descriptions?: string[];
  ackMessage?: boolean;
  redirectPath?: string;
}

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  messageEmitter: Subject<Message> = new Subject<Message>();
  ackMessages: Message[] = [];
  // eventSource?: EventSource;
  retryCount = 5;
  retryEventSource = this.retryCount;
  inReset = false;


  serviceMessage(reponse: any) {
    // console.log( "Service message ", reponse );
    let message: Message = JSON.parse(reponse.data);
    let addMessage = true;

    switch (message.messageType) {
      case MessageType.heartbeat:
        addMessage = false;
        break;
      default:
        // console.log("Service message ", reponse);
        addMessage = true;
        break;
    }
    if (addMessage) {
      this.addMessage(message);
    }
  }

  acknowledgeMessage(message: Message) {
    var index = this.ackMessages.indexOf(message);
    if (index > -1) {
      this.ackMessages.splice(index, 1);
    }
  }

  addMessage(message: Message) {
    if (!message.image) {
      switch (message.messageType) {
        case MessageType.loading:
          // message.icon =  'spinner loading icon';
          break;
        case MessageType.error:
          message.image = 'icons/errorCircle.svg';
          break;
        case MessageType.warning:
          message.image = 'icons/warningCircle.svg';
          break;
        case MessageType.info:
          message.image = 'icons/infoCircle.svg';
          break;
      }
    }
    if (message.ackMessage) {
      this.ackMessages.push(message);
    }
    this.messageEmitter.next(message);
  }

  resetMessages() {
    this.addMessage({ messageType: MessageType.reset, message: "reset" })
  }
}

@Injectable()
export class MoviesErrorHandler implements ErrorHandler {
  constructor(protected messageService: MessageService) {

  }

  notUser = [
    'ExpressionChangedAfterItHasBeenCheckedError'
  ];
  checkNotUser(errorMessage: string) {
    for (let checkNot of this.notUser) {
      if (errorMessage.indexOf(checkNot) > -1) {
        return true;
      }
    }
    return false;
  }

  handleError(error: any) {
    if (error.errorEmmitted || (error.rejection && error.rejection.errorEmmitted)) {
      return;
    }

    console.error("Captured error ", error, typeof error);
    if (error.ngDebugContext && error.ngDebugContext.component) {
      console.log("Error Component ", error.ngDebugContext.component);
    }
    let errorMessage = error;
    if (error.message) {
      errorMessage = error.message;
    }
    else {
      errorMessage = "" + error;
    }
    if (this.checkNotUser(errorMessage)) {
      return;
    }
    let message: Message = { messageType: MessageType.error, message: errorMessage };
    this.messageService.addMessage(message);
  }
}