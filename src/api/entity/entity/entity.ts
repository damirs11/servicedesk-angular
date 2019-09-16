export abstract class Entity {

  name: string;

  constructor() {}

  toString(): string {
    return String(this.name);
  }
}
