import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { throwError, catchError, tap } from 'rxjs';
import { Router } from '@angular/router';

import { Message, MessageType, MessageService } from './message.service';

export class Base {
  id!: string;
  img?: string;
  name!: string;
}
export class Movie extends Base {
  release!:string;
  starring!: string[];
}

export class Person extends Base {
  movies!: string[];
}

export class Response extends Message {
  // responseAction!: ResponseAction;
  responseData?: any;
  description?: string;
  [key:string]: any;
}

@Injectable({ providedIn: 'root' })
export class MoviesService {

  constructor( public router: Router, protected http: HttpClient, protected messageService: MessageService ) {
  }
  
  public handleError( error: HttpResponse<any>  ) {
    // In a real world app, we might use a remote logging infrastructure
    console.error( "Error before trying to concat ", error );
    let whgMessage: Message = { messageType: MessageType.error};
    let errMsg: string;
    if ( error instanceof HttpErrorResponse ) {
      let errorResponse: HttpErrorResponse = error;
      if (errorResponse.error && errorResponse.error.messageType) {
        errMsg = `${errorResponse.error.messageType} - ${errorResponse.error.message || ''}`;
        whgMessage = Object.assign(whgMessage, errorResponse.error);
      }
      else {
        errMsg = `${errorResponse.name} - ${errorResponse.message || ''}`;
        whgMessage.message = errMsg;
        // Message = Object.assign(Message, errorResponse.error);
      }
      
    } else {
      errMsg = error.body && error.body.message ? error.body.message : error.toString();
      whgMessage.message = "Server reported an unknowm Error";
      whgMessage.descriptions = [];
      whgMessage.descriptions.push(errMsg);
    }
    this.messageService.addMessage(whgMessage);
    
    console.error( errMsg );
    let newError = Object.assign( {}, error, {errorEmmitted: true} )
    return throwError( newError );
  }

  serviceGet<T>(method: string, params: any) {

    return this.http.get<T>(method, params).pipe(
      tap(res => this.checkForMessage(res)),
      catchError(error => this.handleError(error)));
  }

  servicePost<T>(method: string, params: any) {

    return this.http.post<T>(method, params).pipe(
      tap(res => this.checkForMessage(res)),
      catchError(error => this.handleError(error)));
  }

  checkForMessage(res: any) {
    if (res && res.messageType) {
      let newMessage: Message = res as Message;
      console.log("Message in response ", newMessage);
      this.messageService.addMessage(newMessage);
    }
  }

}