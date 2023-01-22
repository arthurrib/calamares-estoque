import { IProdutoFornecedor, NewProdutoFornecedor } from './produto-fornecedor.model';

export const sampleWithRequiredData: IProdutoFornecedor = {
  id: 29701,
  valor: 20601,
};

export const sampleWithPartialData: IProdutoFornecedor = {
  id: 84890,
  valor: 5776,
};

export const sampleWithFullData: IProdutoFornecedor = {
  id: 98214,
  valor: 94684,
  observacoes: 'relationships Account',
};

export const sampleWithNewData: NewProdutoFornecedor = {
  valor: 46584,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
