import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { Person } from './person';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class PersonService extends EntityService<Person> {
  constructor($http: HttpClient) {
    super($http);
  }

  // $avatarAttachment;
  // $loadingAvatar = false;

  // get avatarPath(){
  //     if (this.avatarAttachment) return `rest/service/file/download?id=${this.avatarAttachment.id}`;
  //     // Если вложения все нет - асинхронно грузим его, а возвращаем дефолтную аватарку.
  //     if (this.avatarAttachment === undefined && !this.$loadingAvatar) this.$loadAvatarAttachment();

  //     if (this.sex) {
  //         return "img/female-avatar.png";
  //     } else if (this.sex === false) {
  //         return "img/male-avatar.png";
  //     } else {
  //         return "img/default-avatar.png";
  //     }
  // }

  // async $loadAvatarAttachment(){
  //     this.$loadingAvatar = true;
  //     const attachments = await this.getAttachments();
  //     if (!attachments || !attachments.length) {
  //         this.avatarAttachment = null;
  //     } else {
  //         this.avatarAttachment = attachments[0];
  //     }
  //     this.$loadingAvatar = false;
  // }
}
