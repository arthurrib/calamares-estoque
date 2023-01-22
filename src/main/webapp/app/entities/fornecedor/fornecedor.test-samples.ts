import { IFornecedor, NewFornecedor } from './fornecedor.model';

export const sampleWithRequiredData: IFornecedor = {
  id: 32514,
  nome: 'virtual Soap program',
  telefone: 'encryption world-class Afghani',
};

export const sampleWithPartialData: IFornecedor = {
  id: 28615,
  nome: 'and Legacy',
  telefone: 'Sausages',
};

export const sampleWithFullData: IFornecedor = {
  id: 45381,
  nome: 'well-modulated Communications Officer',
  telefone: 'payment',
};

export const sampleWithNewData: NewFornecedor = {
  nome: 'Gloves hacking Quality',
  telefone: 'Handmade Ports transmit',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
