/**
 * Персона
 * @class
 * @name Person
 * @mixes ENTITY_MIXIN.AttachmentsHolder
 */
class Person {
    /**
     * Пол персоны: true(1) - женский, false(0) - мужской
     * @property
     * @name Person#sex
     * @type {boolean}
     */
     sex: boolean;
    /**
     * Почта
     * @property
     * @name Person#email
     * @type {string}
     */
     email: string;
    /**
     * Должность
     * @property
     * @name Person#job
     * @type {string}
     */
     job: string;
    /**
     * Имя
     * @property
     * @name Person#firstName
     * @type {string}
     */
     firstName: string;
    /**
     * Фамилия
     * @property
     * @name Person#lastName
     * @type {string}
     */
     lastName: string;
    /**
     * Отчество
     * @property
     * @name Person#middleName
     * @type {string}
     */
     middleName: string;
    /**
     * Сокращенное имя персоны
     * Фамилия + инициалы
     * @property
     * @name Person#shortName
     * @type {String}
     */
    shortName: string;
    /**
     * Организация персоны
     * @property
     * @name Person#organization
     * @type {Organization}
     */
     organization;

    /**
     * Категория
     * @property
     * @name Person#category
     * @type {EntityCategory}
     */
     category;

    /**
     * Полное имя персоны
     * Фамилия + имя + отчество
     * @property
     * @name Person#shortName
     * @type {String}
     */
    get fullName(){
        return [ this.lastName, this.firstName, this.middleName ]
            .filter(_=>_)
            .join(' ')
        ;
    }

    toString(){
        return this.fullName;
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